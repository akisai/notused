package com.bercut.sa.parentalctl.db;

import com.bercut.sa.parentalctl.rest.model.Children;
import com.bercut.sa.parentalctl.rest.model.Msisdn;
import com.bercut.schema.aoi_parentalctl.GetMsisdnRequestType;

import java.sql.SQLException;

/**
 * Created by haimin-a on 22.03.2019.
 */
public interface RestDbService {

    void addParent(String sessionId, Msisdn msisdn) throws SQLException;

    void addChild(String sessionId, Children children) throws SQLException;

    void delParent(String sessionId, String msisdn) throws SQLException;

    void delChild(String sessionId, String msisdn) throws SQLException;

    void setChild(String sessionId, Msisdn msisdn) throws SQLException;
}
