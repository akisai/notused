package com.bercut.sa.parentalctl.utils;

import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by haimin-a on 22.03.2019.
 */
public class Utils {

    public static String createUuid() {
        return UUID.randomUUID().toString();
    }

    public static void validateMsisdn(String... msisdns) throws SQLException {
        for (String msisdn : msisdns) {
            if (msisdn == null || !msisdn.startsWith("7") || msisdn.length() != 11)
                throw new SQLException("Wrong msisdn", null, 20102);
        }
    }

    public static void validateFlags(Boolean... booleans) throws SQLException {
        for (Boolean bool : booleans) {
            if (bool == null)
                throw new SQLException("Wrong params", null, 20102);
        }
    }
}
