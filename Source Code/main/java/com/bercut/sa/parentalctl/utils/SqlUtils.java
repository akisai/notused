package com.bercut.sa.parentalctl.utils;

import com.bercut.sa.parentalctl.atlas.AtlasProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by haimin-a on 22.03.2019.
 */
@Component
public class SqlUtils {


    private final static Logger logger = LoggerFactory.getLogger(SqlUtils.class);


    private final AtlasProvider atlasProvider;

    @Autowired
    public SqlUtils(AtlasProvider atlasProvider) {
        this.atlasProvider = atlasProvider;
    }

    public Connection getConnection() throws SQLException {
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup(atlasProvider.getDataSource());
            return ds.getConnection();
        } catch (Throwable e) {
            throw new SQLException("Connection refused", e);
        }
    }

    public CallableStatement prepareSingleFunc(Connection conn, String funcName, int paramCnt) throws SQLException {
        StringBuilder sbSql = new StringBuilder().append("{ call ").append(atlasProvider.getSchema()).append(".")
                .append(funcName).append("(");
        for (int i = 0; i < paramCnt; i += 1) {
            if (i != 0) {
                sbSql.append(", ");
            }
            sbSql.append("?");
        }
        sbSql.append(")}");
        logger.debug("Prepare function : " + sbSql.toString());
        return conn.prepareCall(sbSql.toString());
    }

    public void commit(Connection conn) {
        if (conn != null) {
            try {
                conn.commit();
            } catch (SQLException e) {
                logger.warn(e.toString());
            }
        }
    }

    public void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                logger.warn(e.toString());
            }
        }
    }
}
