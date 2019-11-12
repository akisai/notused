package com.bercut.sa.parentalctl.utils;

import com.bercut.sa.parentalctl.atlas.AtlasProvider;
import com.bercut.sa.parentalctl.logs.LoggerText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by haimin-a on 22.03.2019.
 */
@Component
public class SqlUtils {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AtlasProvider atlasProvider;

    @Autowired
    public SqlUtils(AtlasProvider atlasProvider) {
        this.atlasProvider = atlasProvider;
    }

    public Connection getConnection(String sessionId) throws SQLException {
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup(atlasProvider.getDataSource());
            if (logger.isDebugEnabled()) {
                logger.debug(LoggerText.SQL_OPERATION.getText(), sessionId, "getConnection");
            }
            return ds.getConnection();
        } catch (NamingException | ClassCastException e) {
            //logger.warn(LoggerText.DB_ERROR.getText(), sessionId, "getConnection", e.getMessage());
            throw new SQLException("Connection refused: " + e.getMessage(), e);
        }
    }

    public void commit(Connection conn, String sessionId) {
        if (conn != null) {
            try {
                conn.commit();
                if (logger.isDebugEnabled()) {
                    logger.debug(LoggerText.SQL_OPERATION.getText(), sessionId, "commit");
                }
            } catch (SQLException e) {
                logger.warn(LoggerText.DB_ERROR.getText(), sessionId, "commit", e.getMessage());
            }
        }
    }

    public void rollback(Connection conn, String sessionId) {
        if (conn != null) {
            try {
                conn.rollback();
                if (logger.isDebugEnabled()) {
                    logger.debug(LoggerText.SQL_OPERATION.getText(), sessionId, "rollback");
                }
            } catch (SQLException e) {
                logger.warn(LoggerText.DB_ERROR.getText(), sessionId, "rollback", e.getMessage());
            }
        }
    }
}
