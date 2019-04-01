package com.bercut.sa.parentalctl.atlas;

import com.bercut.atlas.entity.iface.ErrorHandler;
import com.bercut.atlas.entity.iface.access_point.ClientAccessPoint;
import com.bercut.atlas.entity.iface.objectmodel.ApplicationObjectModel;
import com.bercut.atlas.entity.iface.objectmodel.AtlasObjectModelFactory;
import com.bercut.atlas.sdk.iface.AtlasException;
import com.bercut.atlas.sdk.iface.Version;
import com.bercut.atlas.sdk.iface.containers.Container;
import com.bercut.atlas.sdk.iface.containers.ContainerType;
import com.bercut.atlas.sdk.iface.containers.ContainersFactoryCreator;
import com.bercut.atlas.sdk.iface.mib.Value;
import com.bercut.atlas.sdk.iface.mib.Variable;
import com.bercut.atlas.sdk.iface.mib.VariableEventsListener;
import com.bercut.common.service.ServiceException;
import com.bercut.sa.parentalctl.atlas.gen.ParentalCtl_Core_ALARMS;
import com.bercut.sa.parentalctl.atlas.gen.ParentalCtl_Core_TRACES;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class AtlasProviderImpl extends AppenderSkeleton implements AtlasProvider {

    private Container container;
    private ApplicationObjectModel aom;
    private ParentalCtl_Core_TRACES trace;
    private ParentalCtl_Core_ALARMS alarms;
    private ClientAccessPoint clientAccessPoint;
    private final String CONNECTION_NAME = "parental";


    @PostConstruct
    public void init() throws ServiceException {
        try {
            this.container = ContainersFactoryCreator.createFactory().createContainer(ContainerType.containerSA, "ParentalCtl", new Version(1, 0, 0, 0));
            this.aom = AtlasObjectModelFactory.createApplicationModel(container, new ErrorHandler() {
                @Override
                public boolean error(Exception e) {
                    return true;
                }

                @Override
                public void error(String s) {
                    System.err.println(s);
                }
            });
            trace = new ParentalCtl_Core_TRACES(container.getTrace());
            alarms = new ParentalCtl_Core_ALARMS(container.getAlarm());
            Logger.getRootLogger().addAppender(this);
            final Variable logLevel = this.aom.getCoreConfig().getVariable("LogLevel");
            setLogLevel(logLevel.readValue().getString());
            logLevel.addEventsListener(new VariableEventsListener() {
                @Override
                public void onChangeValue(Variable variable, Value old, Value new_) {
                    setLogLevel(new_.getString());
                }
            });
            aom.getCoreStatistics().getGroup().getStatisticsVariable("ParentUri").setStringValue("/parent");
            aom.getCoreStatistics().getGroup().getStatisticsVariable("ChildUri").setStringValue("/child");
            clientAccessPoint = aom.getClientAccessPoint("DataSource");
        } catch (AtlasException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @PreDestroy
    public void dispose() {
        if (container != null) {
            container.getTrace().writeMessageString("close");
            container.close();
        }
        Logger.getRootLogger().removeAllAppenders();
    }

    @Override
    protected void append(LoggingEvent event) {
        final LocationInfo l = event.getLocationInformation();
        final StringBuilder sb = new StringBuilder();
        sb.append("(").append(l.getFileName()).append(":").append(l.getLineNumber()).append(") ")
                .append((event.getMessage() == null ? "null" : event.getMessage().toString()));
        final String msg = sb.toString();

        switch (event.getLevel().toInt()) {

            case Level.FATAL_INT:
                alarms.raiseCritical(msg);
                break;
            case Level.ERROR_INT:
                alarms.raiseMajorError(msg);
                break;
            case Level.WARN_INT:
                alarms.raiseWarning(msg);
                break;
            case Level.INFO_INT:
                trace.Minimum(msg);
                break;
            default:
                trace.Debug(msg);

        }
    }

    @Override
    public void close() {
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    private void setLogLevel(String level) {

        trace.Minimum(String.format("Set new logs level \"%s\"", level));

        final Logger root = Logger.getRootLogger();

        switch (level) {
            case "debug":
                root.setLevel(Level.DEBUG);
                break;
            case "maximum":
                root.setLevel(Level.INFO);
                break;
            case "medium":
                root.setLevel(Level.WARN);
                break;
            case "minimum":
                root.setLevel(Level.ERROR);
                break;
            default:
                root.setLevel(Level.ERROR);
        }

    }

    @Override
    public String getDataSource() {
        return clientAccessPoint
                .getConnection(CONNECTION_NAME)
                .getProtocolLayers()
                .getProtocolLayer("JDBC")
                .getVariable("DataSourceName")
                .readValue()
                .getString();
    }
}
