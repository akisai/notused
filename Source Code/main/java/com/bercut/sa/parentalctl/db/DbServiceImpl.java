package com.bercut.sa.parentalctl.db;

import com.bercut.sa.parentalctl.logs.LoggerText;
import com.bercut.sa.parentalctl.rest.RestProcedure;
import com.bercut.sa.parentalctl.rest.model.Children;
import com.bercut.sa.parentalctl.rest.model.Flags;
import com.bercut.sa.parentalctl.rest.model.Msisdn;
import com.bercut.sa.parentalctl.soap.SoapProcedure;
import com.bercut.sa.parentalctl.utils.SqlUtils;
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

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final SqlUtils sqlUtils;

    @Autowired
    public DbServiceImpl(SqlUtils sqlUtils) {
        this.sqlUtils = sqlUtils;
    }

    @Override
    public void addParent(String sessionId, Msisdn msisdn) throws DbException {
        try ( Connection conn = sqlUtils.getConnection(sessionId) ) {
            try ( PreparedStatement psGetChild = conn.prepareStatement(SQLQuery.GET_CHILD) ) {
                psGetChild.setString(1, msisdn.getMsisdn());
                if (logger.isDebugEnabled()) {
                    logger.debug(LoggerText.SQL_REQUEST.getText(), sessionId, RestProcedure.add_parent, SQLQuery.GET_CHILD, msisdn);
                }
                try ( ResultSet rsGetChild = psGetChild.executeQuery() ) {
                    if (rsGetChild.next()) {
                        throw new DbException("Number already assign to children", null, 20100);
                    }
                }
            }
            try ( PreparedStatement psInsertParent = conn.prepareStatement(SQLQuery.INSERT_PARENT) ) {
                psInsertParent.setString(1, msisdn.getMsisdn());
                if (logger.isDebugEnabled()) {
                    logger.debug(LoggerText.SQL_REQUEST.getText(), sessionId, RestProcedure.add_parent, SQLQuery.INSERT_PARENT, msisdn);
                }
                psInsertParent.executeUpdate();
                sqlUtils.commit(conn, sessionId);
            } catch (SQLException e) {
                sqlUtils.rollback(conn, sessionId);
                throw new DbException(e.getMessage(), e.getSQLState(), e.getErrorCode());
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getSQLState(), e.getErrorCode());
        }
    }

    @Override
    public void addChild(String sessionId, Children children) throws DbException {
        try ( Connection conn = sqlUtils.getConnection(sessionId) ) {
            try ( PreparedStatement psGetParent = conn.prepareStatement(SQLQuery.GET_PARENT) ) {
                psGetParent.setString(1, children.getMsisdn());
                if (logger.isDebugEnabled()) {
                    logger.debug(LoggerText.SQL_REQUEST.getText(), sessionId, RestProcedure.add_child, SQLQuery.GET_PARENT, "msisdn=" + children.getMsisdn());
                }
                try ( ResultSet rsGetParent = psGetParent.executeQuery() ) {
                    if (rsGetParent.next()) {
                        throw new DbException("Number already assign to parent", null, 20100);
                    }
                }
                psGetParent.setString(1, children.getParent());
                try ( ResultSet rsGetParent = psGetParent.executeQuery() ) {
                    if (rsGetParent.next()) {
                        try ( PreparedStatement psInsertChild = conn.prepareStatement(SQLQuery.INSERT_CHILD) ) {
                            psInsertChild.setString(1, children.getMsisdn());
                            psInsertChild.setString(2, rsGetParent.getString("ID"));
                            psInsertChild.setInt(3, children.getFwdAoc() ? 1 : 0);
                            psInsertChild.setInt(4, children.getFwdPay() ? 1 : 0);
                            if (logger.isDebugEnabled()) {
                                logger.debug(LoggerText.SQL_REQUEST.getText(), sessionId, RestProcedure.add_child, SQLQuery.GET_PARENT, children);
                            }
                            psInsertChild.executeUpdate();
                            sqlUtils.commit(conn, sessionId);
                        } catch (SQLException e) {
                            sqlUtils.rollback(conn, sessionId);
                            throw new DbException(e.getMessage(), e.getSQLState(), e.getErrorCode());
                        }
                    } else {
                        throw new DbException("Haven't requested parent", null, 20100);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getSQLState(), e.getErrorCode());
        }
    }

    @Override
    public void delParent(String sessionId, String msisdn) throws DbException {
        try ( Connection conn = sqlUtils.getConnection(sessionId) ) {
            try ( PreparedStatement psDeleteParent = conn.prepareStatement(SQLQuery.DELETE_PARENT) ) {
                psDeleteParent.setString(1, msisdn);
                if (logger.isDebugEnabled()) {
                    logger.debug(LoggerText.SQL_REQUEST.getText(), sessionId, RestProcedure.delete_parent, SQLQuery.DELETE_PARENT, "msisdn=" + msisdn);
                }
                if (psDeleteParent.executeUpdate() == 1) {
                    sqlUtils.commit(conn, sessionId);
                } else {
                    throw new DbException("Haven't requested parent", null, 20103);
                }
            } catch (SQLException e) {
                sqlUtils.rollback(conn, sessionId);
                if (e.getErrorCode() == 2292) {
                    throw new DbException(e.getMessage(), e.getSQLState(), 20100);
                }
                throw new DbException(e.getMessage(), e.getSQLState(), e.getErrorCode());
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getSQLState(), e.getErrorCode());
        }
    }

    @Override
    public void delChild(String sessionId, String msisdn) throws DbException {
        try ( Connection conn = sqlUtils.getConnection(sessionId) ) {
            try ( PreparedStatement psDeleteChildren = conn.prepareStatement(SQLQuery.DELETE_CHILD) ) {
                psDeleteChildren.setString(1, msisdn);
                if (logger.isDebugEnabled()) {
                    logger.debug(LoggerText.SQL_REQUEST.getText(), sessionId, RestProcedure.delete_child, SQLQuery.DELETE_CHILD, "msisdn=" + msisdn);
                }
                if (psDeleteChildren.executeUpdate() == 1) {
                    sqlUtils.commit(conn, sessionId);
                } else {
                    throw new DbException("Haven't requested children", null, 20103);
                }
            } catch (SQLException e) {
                sqlUtils.rollback(conn, sessionId);
                throw new DbException(e.getMessage(), e.getSQLState(), e.getErrorCode());
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getSQLState(), e.getErrorCode());
        }
    }

    @Override
    public void setChild(String sessionId, String msisdn, Flags flags) throws DbException {
        try ( Connection conn = sqlUtils.getConnection(sessionId) ) {
            try ( PreparedStatement psUpdateChild = conn.prepareStatement(SQLQuery.UPDATE_CHILD) ) {
                psUpdateChild.setInt(1, flags.getFwdAoc() ? 1 : 0);
                psUpdateChild.setInt(2, flags.getFwdPay() ? 1 : 0);
                psUpdateChild.setString(3, msisdn);
                if (logger.isDebugEnabled()) {
                    logger.debug(LoggerText.SQL_REQUEST.getText(), sessionId, RestProcedure.set_child, SQLQuery.UPDATE_CHILD, "msisdn=" + msisdn + "\n" + flags);
                }
                if (psUpdateChild.executeUpdate() == 1) {
                    sqlUtils.commit(conn, sessionId);
                } else {
                    throw new DbException("Haven't requested children", null, 20103);
                }
            } catch (SQLException e) {
                sqlUtils.rollback(conn, sessionId);
                throw new DbException(e.getMessage(), e.getSQLState(), e.getErrorCode());
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getSQLState(), e.getErrorCode());
        }
    }

    @Override
    public GetMsisdnResponse getAbonentType(String sessionId, String msisdn) throws DbException {
        GetMsisdnResponse response = new GetMsisdnResponse();
        try ( Connection conn = sqlUtils.getConnection(sessionId) ) {
            try ( PreparedStatement psGetParent = conn.prepareStatement(SQLQuery.GET_PARENT) ) {
                psGetParent.setString(1, msisdn);
                if (logger.isDebugEnabled()) {
                    logger.debug(LoggerText.SQL_REQUEST.getText(), sessionId, SoapProcedure.get_msisdn, SQLQuery.GET_PARENT, "msisdn=" + msisdn);
                }
                try ( ResultSet rsGetParent = psGetParent.executeQuery() ) {
                    if (rsGetParent.next()) {
                        response.setResult("ok_parent");
                        response.setMsisdn(rsGetParent.getString("MSISDN"));
                        return response;
                    }
                }
            }
            try ( PreparedStatement psGetFullChild = conn.prepareStatement(SQLQuery.GET_FULL_CHILD) ) {
                psGetFullChild.setString(1, msisdn);
                if (logger.isDebugEnabled()) {
                    logger.debug(LoggerText.SQL_REQUEST.getText(), sessionId, SoapProcedure.get_msisdn, SQLQuery.GET_FULL_CHILD, "msisdn=" + msisdn);
                }
                try ( ResultSet rsGetFullChild = psGetFullChild.executeQuery() ) {
                    if (rsGetFullChild.next()) {
                        response.setResult("ok_children");
                        response.setMsisdn(rsGetFullChild.getString("MSISDN"));
                        response.setParent(rsGetFullChild.getString("PARENT"));
                        response.setFwdAoc(rsGetFullChild.getInt("FWD_AOC") == 1);
                        response.setFwdPay(rsGetFullChild.getInt("FWD_PAY") == 1);
                        return response;
                    }
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getSQLState(), e.getErrorCode());
        }
        response.setResult("err_notFound");
        return response;
    }
}
