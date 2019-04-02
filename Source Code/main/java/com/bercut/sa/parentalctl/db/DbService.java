package com.bercut.sa.parentalctl.db;

import com.bercut.sa.parentalctl.rest.model.Children;
import com.bercut.sa.parentalctl.rest.model.Flags;
import com.bercut.sa.parentalctl.rest.model.Msisdn;
import com.bercut.schema.aoi_parentalctl.GetMsisdnResponseType;

/**
 * Created by haimin-a on 22.03.2019.
 */
public interface DbService {

    void addParent(String sessionId, Msisdn msisdn) throws DbException;

    void addChild(String sessionId, Children children) throws DbException;

    void delParent(String sessionId, String msisdn) throws DbException;

    void delChild(String sessionId, String msisdn) throws DbException;

    void setChild(String sessionId, String msisdn, Flags flags) throws DbException;

    GetMsisdnResponseType getMsisdn(String sessionId, String msisdn) throws DbException;
}
