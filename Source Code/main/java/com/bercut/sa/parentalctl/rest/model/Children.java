package com.bercut.sa.parentalctl.rest.model;

/**
 * Created by haimin-a on 25.03.2019.
 */
public class Children {

    private String msisdn;
    private String parent;
    private Boolean fwdAoc;
    private Boolean fwdPay;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Boolean getFwdAoc() {
        return fwdAoc;
    }

    public void setFwdAoc(Boolean fwdAoc) {
        this.fwdAoc = fwdAoc;
    }

    public Boolean getFwdPay() {
        return fwdPay;
    }

    public void setFwdPay(Boolean fwdPay) {
        this.fwdPay = fwdPay;
    }

    @Override
    public String toString() {
        return "msisdn=" + msisdn +
                ", parent=" + parent +
                ", fwdAoc=" + fwdAoc +
                ", fwdPay=" + fwdPay;
    }
}
