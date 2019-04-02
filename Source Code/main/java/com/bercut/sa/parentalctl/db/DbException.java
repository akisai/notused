package com.bercut.sa.parentalctl.db;

import java.sql.SQLException;

/**
 * Created by haimin-a on 02.04.2019.
 */
public class DbException extends SQLException {

    public DbException() {
        super();
    }

    public DbException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }
}
