package com.bercut.sa.parentalctl.db;

import com.bercut.sa.parentalctl.TestConfig;
import com.bercut.sa.parentalctl.rest.model.Children;
import com.bercut.sa.parentalctl.rest.model.Flags;
import com.bercut.sa.parentalctl.rest.model.Msisdn;
import com.bercut.schema.aoi_parentalctl.GetMsisdnResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import java.sql.SQLException;

import static com.bercut.sa.parentalctl.rest.ParentalctlRestControllerTest.CONFLICT;
import static com.bercut.sa.parentalctl.rest.ParentalctlRestControllerTest.DUPLICATE;
import static com.bercut.sa.parentalctl.soap.ParentalCtlSoapImplTest.CHILDREN;
import static com.bercut.sa.parentalctl.soap.ParentalCtlSoapImplTest.PARENT;

/**
 * Created by haimin-a on 29.03.2019.
 */

@ContextConfiguration(classes = TestConfig.class)
public class DbServiceImplTestImpl implements DbService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void addParent(String sessionId, Msisdn msisdn) throws SQLException {
        switch (msisdn.getMsisdn()) {
            case CONFLICT:
                throw new SQLException("Number already assign to children", null, 20100);
            case DUPLICATE:
                throw new SQLException("Duplicate", null, 1);
            default:
        }
    }

    @Override
    public void addChild(String sessionId, Children children) throws SQLException {
        switch (children.getMsisdn()) {
            case CONFLICT:
                throw new SQLException("Number already assign to parent", null, 20100);
            case DUPLICATE:
                throw new SQLException("Duplicate", null, 1);
            default:
        }
    }

    @Override
    public void delParent(String sessionId, String msisdn) throws SQLException {
        switch (msisdn) {
            case DUPLICATE:
                throw new SQLException("Haven't requested parent", null, 20103);
            case CONFLICT:
                throw new SQLException("Parent has children yet", null, 20100);
            default:
        }
    }

    @Override
    public void delChild(String sessionId, String msisdn) throws SQLException {
        if (DUPLICATE.equals(msisdn)) {
            throw new SQLException("Haven't requested children", null, 20103);
        }
    }

    @Override
    public void setChild(String sessionId, String msisdn, Flags flags) throws SQLException {
        if (DUPLICATE.equals(msisdn)) {
            throw new SQLException("Haven't requested children", null, 20103);
        }
    }

    @Override
    public GetMsisdnResponseType getMsisdn(String sessionId, String msisdn) throws SQLException {
        GetMsisdnResponseType response = new GetMsisdnResponseType();
        switch (msisdn) {
            case PARENT:
                response.setResult("ok_parent");
                return response;
            case CHILDREN:
                response.setResult("ok_children");
                return response;
            default:
                response.setResult("err_notFound");
                return response;
        }
    }
}