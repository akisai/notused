package com.bercut.sa.parentalctl.db;

import com.bercut.sa.parentalctl.TestConfig;
import com.bercut.sa.parentalctl.rest.model.Children;
import com.bercut.sa.parentalctl.rest.model.Flags;
import com.bercut.sa.parentalctl.rest.model.Msisdn;
import com.bercut.schema.aoi_parentalctl.GetMsisdnResponseType;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

/**
 * Created by haimin-a on 29.03.2019.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class DbServiceImplTest implements DbService{

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Override
    public void addParent(String sessionId, Msisdn msisdn) throws SQLException {

    }

    @Override
    public void addChild(String sessionId, Children children) throws SQLException {

    }

    @Override
    public void delParent(String sessionId, String msisdn) throws SQLException {

    }

    @Override
    public void delChild(String sessionId, String msisdn) throws SQLException {

    }

    @Override
    public void setChild(String sessionId, String msisdn, Flags flags) throws SQLException {

    }

    @Override
    public GetMsisdnResponseType getMsisdn(String sessionId, String msisdn) throws SQLException {
        return null;
    }
}