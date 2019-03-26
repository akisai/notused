package com.bercut.sa.parentalctl.db;

import com.bercut.sa.parentalctl.rest.model.Children;
import com.bercut.sa.parentalctl.rest.model.Flags;
import com.bercut.sa.parentalctl.rest.model.Msisdn;
import com.bercut.sa.parentalctl.utils.SqlUtils;
import com.bercut.schema.aoi_parentalctl.GetMsisdnRequestType;
import com.bercut.schema.aoi_parentalctl.GetMsisdnResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by haimin-a on 22.03.2019.
 */

@Component
public class DbServiceImpl implements DbService {

    private final Logger logger = LoggerFactory.getLogger(DbService.class);
    private final SqlUtils sqlUtils;

    @Autowired
    public DbServiceImpl(SqlUtils sqlUtils) {
        this.sqlUtils = sqlUtils;
    }

    @Override
    public void addParent(String sessionId, Msisdn msisdn) throws SQLException {
        try ( Connection conn = sqlUtils.getConnection();
              PreparedStatement psGetChild = conn.prepareStatement(SQLQuery.GET_CHILD);
              PreparedStatement psInsertParent = conn.prepareStatement(SQLQuery.INSERT_PARENT)) {
            psGetChild.setString(1, msisdn.getMsisdn());
            psInsertParent.setString(1, msisdn.getMsisdn());
            try (ResultSet rsGetChild = psGetChild.executeQuery()) {
                while(rsGetChild.next()) {
                    if (rsGetChild.getString("msisdn") != null)
                        throw new SQLException("Number already assign to children", null, 20100);
                }
            }
            try {
                psInsertParent.executeUpdate();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
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
    public GetMsisdnResponseType getMsisdn(String sessionId, GetMsisdnRequestType msisdn) throws SQLException {
        return null;
    }
}
