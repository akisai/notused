package com.bercut.sa.parentalctl.utils;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by haimin-a on 29.03.2019.
 */
public class UtilsTest {

    @Test
    public void createUuid() {
        Assert.assertNotNull(Utils.createUuid());
    }

    @Test(expected = SQLException.class)
    public void validateMsisdnNull() throws SQLException {
        String msisdn = null;
        Utils.validateMsisdn(msisdn);
    }

    @Test(expected = SQLException.class)
    public void validateMsisdnStartSymbol() throws SQLException {
        String msisdn = "89995257229";
        Utils.validateMsisdn(msisdn);
    }

    @Test(expected = SQLException.class)
    public void validateMsisdnLenght() throws SQLException {
        String msisdn = "7888999";
        Utils.validateMsisdn(msisdn);
    }

    @Test(expected = SQLException.class)
    public void validateFlags() throws SQLException {
        Boolean flag1 = true;
        Boolean flag2 = null;
        Utils.validateFlags(flag1, flag2);
    }
}