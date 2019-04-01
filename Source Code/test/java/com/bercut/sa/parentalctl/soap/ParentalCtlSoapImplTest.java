package com.bercut.sa.parentalctl.soap;

import com.bercut.mb.sdk.request.ServerRequestParameters;
import com.bercut.sa.parentalctl.ServerRequestParametersImplTest;
import com.bercut.sa.parentalctl.TestConfig;
import com.bercut.sa.parentalctl.db.DbService;
import com.bercut.schema.aoi_parentalctl.GetMsisdnRequestType;
import com.bercut.wsdl.parentalctl.SqlExceptionException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.ServletContext;

/**
 * Created by haimin-a on 29.03.2019.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
public class ParentalCtlSoapImplTest {

    public final static String PARENT = "79995257229";
    public final static String CHILDREN = "79995257119";
    private final static String ERROR = "79995257009";
    @Autowired
    DbService dbService;
    @Autowired
    ServletContext servletContext;
    @Test
    public void parentalCtlOperationParent() throws SqlExceptionException {
        GetMsisdnRequestType params = new GetMsisdnRequestType();
        params.setMsisdn(PARENT);
        ServerRequestParameters requestParameters = new ServerRequestParametersImplTest();
        Assert.assertEquals("ok_parent", new ParentalCtlSoapImpl(servletContext, dbService).parentalCtlOperation(params, requestParameters).getResult());
    }

    @Test
    public void parentalCtlOperationChild() throws SqlExceptionException {
        GetMsisdnRequestType params = new GetMsisdnRequestType();
        params.setMsisdn(CHILDREN);
        ServerRequestParameters requestParameters = new ServerRequestParametersImplTest();
        Assert.assertEquals("ok_children", new ParentalCtlSoapImpl(servletContext, dbService).parentalCtlOperation(params, requestParameters).getResult());
    }

    @Test
    public void parentalCtlOperationError() throws SqlExceptionException {
        GetMsisdnRequestType params = new GetMsisdnRequestType();
        params.setMsisdn(ERROR);
        ServerRequestParameters requestParameters = new ServerRequestParametersImplTest();
        Assert.assertEquals("err_notFound", new ParentalCtlSoapImpl(servletContext, dbService).parentalCtlOperation(params, requestParameters).getResult());
    }
}