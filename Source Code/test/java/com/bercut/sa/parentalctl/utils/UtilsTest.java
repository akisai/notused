package com.bercut.sa.parentalctl.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by haimin-a on 29.03.2019.
 */
public class UtilsTest {

    @Test
    public void createUuid() {
        Assert.assertNotNull(Utils.createUuid());
    }

    @Test(expected = ValidateException.class)
    public void validateMsisdnNull() throws ValidateException {
        String msisdn = null;
        Utils.validateMsisdn(msisdn);
    }

    @Test(expected = ValidateException.class)
    public void validateMsisdnStartSymbol() throws ValidateException {
        String msisdn = "89995257229";
        Utils.validateMsisdn(msisdn);
    }

    @Test(expected = ValidateException.class)
    public void validateMsisdnLenght() throws ValidateException {
        String msisdn = "7888999";
        Utils.validateMsisdn(msisdn);
    }

    @Test(expected = ValidateException.class)
    public void validateFlags() throws ValidateException {
        Boolean flag1 = true;
        Boolean flag2 = null;
        Utils.validateFlags(flag1, flag2);
    }
}