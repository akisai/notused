package com.bercut.sa.parentalctl.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by haimin-a on 25.03.2019.
 */
public class Flags {

    @JsonProperty("fwd_aoc")
    private Boolean fwdAoc;
    @JsonProperty("fwd_pay")
    private Boolean fwdPay;

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
        return "fwdAoc=" + fwdAoc +
                ", fwdPay=" + fwdPay;
    }
}
