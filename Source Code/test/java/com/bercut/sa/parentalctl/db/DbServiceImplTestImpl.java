package com.bercut.sa.parentalctl.db;

import com.bercut.sa.parentalctl.TestConfig;
import com.bercut.sa.parentalctl.rest.ParentalctlRestControllerTest;
import com.bercut.sa.parentalctl.rest.model.Children;
import com.bercut.sa.parentalctl.rest.model.Flags;
import com.bercut.sa.parentalctl.rest.model.Msisdn;
import com.bercut.sa.parentalctl.soap.ParentalCtlSoapImplTest;
import com.bercut.schema.aoi_parentalctl.GetMsisdnResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
/**
 * Created by haimin-a on 29.03.2019.
 */

@ContextConfiguration(classes = TestConfig.class)
public class DbServiceImplTestImpl implements DbService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void addParent(String sessionId, Msisdn msisdn) throws DbException {
        switch (msisdn.getMsisdn()) {
            case ParentalctlRestControllerTest.CONFLICT:
                throw new DbException("Number already assign to children", null, 20100);
            case ParentalctlRestControllerTest.DUPLICATE:
                throw new DbException("Duplicate", null, 1);
            default:
        }
    }

    @Override
    public void addChild(String sessionId, Children children) throws DbException {
        switch (children.getMsisdn()) {
            case ParentalctlRestControllerTest.CONFLICT:
                throw new DbException("Number already assign to parent", null, 20100);
            case ParentalctlRestControllerTest.DUPLICATE:
                throw new DbException("Duplicate", null, 1);
            default:
        }
    }

    @Override
    public void delParent(String sessionId, String msisdn) throws DbException {
        switch (msisdn) {
            case ParentalctlRestControllerTest.DUPLICATE:
                throw new DbException("Haven't requested parent", null, 20103);
            case ParentalctlRestControllerTest.CONFLICT:
                throw new DbException("Parent has children yet", null, 20100);
            default:
        }
    }

    @Override
    public void delChild(String sessionId, String msisdn) throws DbException {
        if (ParentalctlRestControllerTest.DUPLICATE.equals(msisdn)) {
            throw new DbException("Haven't requested children", null, 20103);
        }
    }

    @Override
    public void setChild(String sessionId, String msisdn, Flags flags) throws DbException {
        if (ParentalctlRestControllerTest.DUPLICATE.equals(msisdn)) {
            throw new DbException("Haven't requested children", null, 20103);
        }
    }

    @Override
    public GetMsisdnResponseType getAbonentType(String sessionId, String msisdn) throws DbException {
        GetMsisdnResponseType response = new GetMsisdnResponseType();
        switch (msisdn) {
            case ParentalCtlSoapImplTest.PARENT:
                response.setResult("ok_parent");
                return response;
            case ParentalCtlSoapImplTest.CHILDREN:
                response.setResult("ok_children");
                return response;
            default:
                response.setResult("err_notFound");
                return response;
        }
    }
}