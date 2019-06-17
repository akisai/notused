package com.bercut.sa.parentalctl.db;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by haimin-a on 17.06.2019.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetMsisdnResponse {
    private String result;
    private String msisdn;
    private String parent;
    private Boolean fwdAoc;
    private Boolean fwdPay;

    public GetMsisdnResponse() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

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
}
