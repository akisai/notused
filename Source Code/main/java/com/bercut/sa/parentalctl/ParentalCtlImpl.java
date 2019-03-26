package com.bercut.sa.parentalctl;

import com.bercut.lwsacontainer.stuff.MessageBusProvider;
import com.bercut.mb.sdk.MessageBus;
import com.bercut.mb.sdk.MessageBusFault;
import com.bercut.mb.sdk.request.ServerRequestParameters;
import com.bercut.sa.parentalctl.db.SoapDbService;
import com.bercut.schema.aoi_parentalctl.GetMsisdnRequestType;
import com.bercut.schema.aoi_parentalctl.GetMsisdnResponseType;
import com.bercut.wsdl.parentalctl.ParentalCtlPortType;
import com.bercut.wsdl.parentalctl.ParentalCtlPortTypeSynchServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

@Component
public class ParentalCtlImpl implements ParentalCtlPortType {

    private final Logger logger = LoggerFactory.getLogger(ParentalCtlImpl.class);
    private ParentalCtlPortTypeSynchServer parentalctlServise;

    private final ServletContext servletContext;
    private final SoapDbService soapDbService;

    @Autowired
    public ParentalCtlImpl(ServletContext servletContext, SoapDbService soapDbService) {
        this.servletContext = servletContext;
        this.soapDbService = soapDbService;
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
    public GetMsisdnResponseType getMsisdn(GetMsisdnRequestType params, ServerRequestParameters requestParameters) {
        return null;
    }
}
