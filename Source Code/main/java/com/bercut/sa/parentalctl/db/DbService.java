package com.bercut.sa.parentalctl.db;

import com.bercut.sa.parentalctl.rest.model.Children;
import com.bercut.sa.parentalctl.rest.model.Flags;
import com.bercut.sa.parentalctl.rest.model.Msisdn;
import com.bercut.schema.aoi_parentalctl.GetMsisdnRequestType;
import com.bercut.schema.aoi_parentalctl.GetMsisdnResponseType;

import java.sql.SQLException;

/**
 * Created by haimin-a on 22.03.2019.
 */
public interface DbService {

    void addParent(String sessionId, Msisdn msisdn) throws SQLException;

    void addChild(String sessionId, Children children) throws SQLException;

    void delParent(String sessionId, String msisdn) throws SQLException;

    void delChild(String sessionId, String msisdn) throws SQLException;

    void setChild(String sessionId, String msisdn, Flags flags) throws SQLException;

    GetMsisdnResponseType getMsisdn(String sessionId, GetMsisdnRequestType msisdn) throws SQLException;
}
