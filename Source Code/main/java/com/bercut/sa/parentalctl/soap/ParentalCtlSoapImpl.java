package com.bercut.sa.parentalctl.soap;

import com.bercut.lwsacontainer.stuff.MessageBusProvider;
import com.bercut.mb.sdk.MessageBus;
import com.bercut.mb.sdk.MessageBusFault;
import com.bercut.mb.sdk.request.ServerRequestParameters;
import com.bercut.sa.parentalctl.db.DbService;
import com.bercut.sa.parentalctl.logs.LoggerText;
import com.bercut.sa.parentalctl.utils.Utils;
import com.bercut.schema.aoi_parentalctl.GetMsisdnRequestType;
import com.bercut.schema.aoi_parentalctl.GetMsisdnResponseType;
import com.bercut.schema.aoi_parentalctl.SqlException;
import com.bercut.wsdl.parentalctl.ParentalCtlPortType;
import com.bercut.wsdl.parentalctl.ParentalCtlPortTypeSynchServer;
import com.bercut.wsdl.parentalctl.SqlExceptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.sql.SQLException;
import java.util.Date;

@Component
public class ParentalCtlSoapImpl implements ParentalCtlPortType {

    private final Logger logger = LoggerFactory.getLogger(ParentalCtlSoapImpl.class);
    private ParentalCtlPortTypeSynchServer parentalctlServise;
    private final ServletContext servletContext;
    private final DbService dbService;

    @Autowired
    public ParentalCtlSoapImpl(ServletContext servletContext, DbService dbService) {
        this.servletContext = servletContext;
        this.dbService = dbService;
    }

    @PostConstruct
    public void init() throws MessageBusFault {
        MessageBus mb = MessageBusProvider.getMessageBus(new ServletContextEvent(servletContext));
        parentalctlServise = ParentalCtlPortTypeSynchServer.registerComponentWithQueue(mb, this);
        parentalctlServise.setComponentAvailable();
    }

    @PreDestroy
    public void destroy() {
        if (parentalctlServise != null) {
            parentalctlServise.setComponentUnAvailable();
            parentalctlServise.removeComponent();
        }
    }

    @Override
    public GetMsisdnResponseType parentalCtlOperation(GetMsisdnRequestType params, ServerRequestParameters requestParameters) throws SqlExceptionException {
        String sessionId = requestParameters.getContext().getUuid().toString();
        GetMsisdnResponseType response;
        try {
            Utils.validateMsisdn(params.getMsisdn());
            long startExec = new Date().getTime();

            response = dbService.getMsisdn(sessionId, params.getMsisdn());
            long endExec = new Date().getTime();
            if (logger.isDebugEnabled()) {
                logger.debug(LoggerText.SQL_RESPONSE.getText(), sessionId, SoapProcedure.get_msisdn, endExec - startExec);
            }
        } catch (SQLException e) {
            logger.error(LoggerText.SQL_ERROR.getText(), sessionId, SoapProcedure.get_msisdn, e.getMessage());
            throw new SqlExceptionException("GetMsisdn fault", new SqlException(e.getMessage(), e.getErrorCode()));
        }
        return response;
    }
}
