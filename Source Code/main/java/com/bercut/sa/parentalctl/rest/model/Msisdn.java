package com.bercut.sa.parentalctl.rest.model;

/**
 * Created by haimin-a on 22.03.2019.
 */
public class Msisdn {
    private String msisdn;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Override
    public String toString() {
        return "msisdn=" + msisdn;
    }
}
