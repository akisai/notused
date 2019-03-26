package com.bercut.sa.parentalctl.db;

import com.bercut.sa.parentalctl.logs.LoggerText;
import com.bercut.sa.parentalctl.rest.RestProcedure;
import com.bercut.sa.parentalctl.rest.model.Children;
import com.bercut.sa.parentalctl.rest.model.Flags;
import com.bercut.sa.parentalctl.rest.model.Msisdn;
import com.bercut.sa.parentalctl.soap.SoapProcedure;
import com.bercut.sa.parentalctl.utils.SqlUtils;
import com.bercut.schema.aoi_parentalctl.GetMsisdnRequestType;
import com.bercut.schema.aoi_parentalctl.GetMsisdnResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

/**
 * Created by haimin-a on 26.03.2019.
 */
@Component
public class DbServiceOldImpl implements DbService {

    private final Logger logger = LoggerFactory.getLogger(DbServiceOldImpl.class);
    private final SqlUtils sqlUtils;

    @Autowired
    public DbServiceOldImpl(SqlUtils sqlUtils) {
        this.sqlUtils = sqlUtils;
    }

    @Override
    public GetMsisdnResponseType getMsisdn(String sessionId, GetMsisdnRequestType msisdn) throws SQLException {
        GetMsisdnResponseType response;
        try ( Connection conn = sqlUtils.getConnection();
              CallableStatement func = sqlUtils.prepareSingleFunc(conn, SoapProcedure.get_msisdn.toString(), 1) ) {
            func.setString(1, msisdn.getMsisdn());
            func.registerOutParameter(2, Types.VARCHAR);
            func.registerOutParameter(3, Types.VARCHAR);
            func.registerOutParameter(4, Types.VARCHAR);
            func.registerOutParameter(5, Types.INTEGER);
            func.registerOutParameter(6, Types.INTEGER);
            long startExec = new Date().getTime();
            func.execute();
            long endExec = new Date().getTime();
            logger.debug(LoggerText.SQL_RESPONSE.getText(), sessionId, SoapProcedure.get_msisdn, startExec - endExec);

            response = new GetMsisdnResponseType(
                    func.getString(2),
                    func.getString(3),
                    func.getString(4),
                    func.getInt(5) == 1,
                    func.getInt(6) == 1);
        }

        return response;
    }

    @Override
    public void addParent(String sessionId, Msisdn msisdn) throws SQLException {
        try ( Connection conn = sqlUtils.getConnection();
              CallableStatement func = sqlUtils.prepareSingleFunc(conn, RestProcedure.add_parent.toString(), 1) ) {
            func.setString(1, msisdn.getMsisdn());
            long startExec = new Date().getTime();
            func.execute();
            long endExec = new Date().getTime();
            logger.debug(LoggerText.SQL_RESPONSE.getText(), sessionId, RestProcedure.add_parent, startExec - endExec);
        }
    }

    @Override
    public void addChild(String sessionId, Children children) throws SQLException {
        try ( Connection conn = sqlUtils.getConnection();
              CallableStatement func = sqlUtils.prepareSingleFunc(conn, RestProcedure.add_children.toString(), 4) ) {
            func.setString(1, children.getMsisdn());
            func.setString(2, children.getParent());
            func.setLong(3, children.getFwdAoc() ? 1 : 0);
            func.setLong(4, children.getFwdPay() ? 1 : 0);
            long startExec = new Date().getTime();
            func.execute();
            long endExec = new Date().getTime();
            logger.debug(LoggerText.SQL_RESPONSE.getText(), sessionId, RestProcedure.add_children, startExec - endExec);
        }
    }

    @Override
    public void delParent(String sessionId, String msisdn) throws SQLException {
        try ( Connection conn = sqlUtils.getConnection();
              CallableStatement func = sqlUtils.prepareSingleFunc(conn, RestProcedure.delete_parent.toString(), 1) ) {
            func.setString(1, msisdn);
            long startExec = new Date().getTime();
            func.execute();
            long endExec = new Date().getTime();
            logger.debug(LoggerText.SQL_RESPONSE.getText(), sessionId, RestProcedure.delete_parent, startExec - endExec);
        }
    }

    @Override
    public void delChild(String sessionId, String msisdn) throws SQLException {
        try ( Connection conn = sqlUtils.getConnection();
              CallableStatement func = sqlUtils.prepareSingleFunc(conn, RestProcedure.delete_children.toString(), 1) ) {
            func.setString(1, msisdn);
            long startExec = new Date().getTime();
            func.execute();
            long endExec = new Date().getTime();
            logger.debug(LoggerText.SQL_RESPONSE.getText(), sessionId, RestProcedure.delete_children, startExec - endExec);
        }
    }

    @Override
    public void setChild(String sessionId, String msisdn, Flags flags) throws SQLException {
        try ( Connection conn = sqlUtils.getConnection();
              CallableStatement func = sqlUtils.prepareSingleFunc(conn, RestProcedure.set_children.toString(), 3) ) {
            func.setString(1, msisdn);
            func.setLong(2, flags.getFwdAoc() ? 1 : 0);
            func.setLong(3, flags.getFwdPay() ? 1 : 0);
            long startExec = new Date().getTime();
            func.execute();
            long endExec = new Date().getTime();
            logger.debug(LoggerText.SQL_RESPONSE.getText(), sessionId, RestProcedure.set_children, startExec - endExec);
        }
    }
}
