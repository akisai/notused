package com.bercut.sa.parentalctl.db;

import com.bercut.sa.parentalctl.logs.LoggerText;
import com.bercut.sa.parentalctl.rest.Procedure;
import com.bercut.sa.parentalctl.rest.model.Children;
import com.bercut.sa.parentalctl.rest.model.Msisdn;
import com.bercut.sa.parentalctl.utils.SqlUtils;
import com.bercut.schema.aoi_parentalctl.GetMsisdnRequestType;
import com.bercut.schema.aoi_parentalctl.GetMsisdnResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Date;
import java.sql.SQLException;

/**
 * Created by haimin-a on 22.03.2019.
 */

@Component
public class DbServiceImpl implements RestDbService, SoapDbService {

    private final SqlUtils sqlUtils;

    @Autowired
    public DbServiceImpl(SqlUtils sqlUtils) {
        this.sqlUtils = sqlUtils;
    }

    private final Logger logger = LoggerFactory.getLogger(DbServiceImpl.class);

    @Override
    public GetMsisdnResponseType getMsisdn(String sessionId, GetMsisdnRequestType msisdn) {
        return null;
    }

    @Override
    public void addParent(String sessionId, Msisdn msisdn) throws SQLException {
        try ( Connection conn = sqlUtils.getConnection();
              CallableStatement func = sqlUtils.prepareSingleFunc(conn, Procedure.add_parent.toString(), 1) ) {
            validateMsisdn(msisdn.getMsisdn());
            func.setString(1, msisdn.getMsisdn());
            long startExec = new Date().getTime();
            func.execute();
            long endExec = new Date().getTime();
            logger.debug(LoggerText.SQL_RESPONSE.getText(), sessionId, Procedure.add_parent, startExec - endExec);
        } catch (SQLException e) {
            logger.error(LoggerText.SQL_ERROR.getText(), sessionId, Procedure.add_parent, e.getMessage());
            throw new SQLException(e);
        }
    }

    @Override
    public void addChild(String sessionId, Children children) throws SQLException {

    }

    @Override
    public void delParent(String sessionId, String msisdn) throws SQLException {
        try ( Connection conn = sqlUtils.getConnection();
              CallableStatement func = sqlUtils.prepareSingleFunc(conn, Procedure.delete_parent.toString(), 1) ) {
            validateMsisdn(msisdn);
            func.setString(1, msisdn);
            long startExec = new Date().getTime();
            func.execute();
            long endExec = new Date().getTime();
            logger.debug(LoggerText.SQL_RESPONSE.getText(), sessionId, Procedure.delete_parent, startExec - endExec);
        } catch (SQLException e) {
            logger.error(LoggerText.SQL_ERROR.getText(), sessionId, Procedure.delete_parent, e.getMessage());
            logger.error(String.valueOf(e.getErrorCode()));
            throw new SQLException(e.getMessage(), e.getSQLState(), e.getErrorCode());
        }
    }

    @Override
    public void delChild(String sessionId, String msisdn) throws SQLException {

    }

    @Override
    public void setChild(String sessionId, Msisdn msisdn) throws SQLException {

    }

    private void validateMsisdn(String msisdn) throws SQLException {
        if (!msisdn.startsWith("7") || msisdn.length() != 11)
            throw new SQLException("Wrong msisdn", null, 20102);
    }
}
