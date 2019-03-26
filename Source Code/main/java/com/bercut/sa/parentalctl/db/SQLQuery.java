package com.bercut.sa.parentalctl.db;

/**
 * Created by haimin-a on 26.03.2019.
 */
class SQLQuery {
    static String GET_PARENT = "SELECT * FROM PARENT WHERE MSISDN = ?";
    static String GET_CHILD = "SELECT * FROM CHILD WHERE MSISDN = ?";
    static String INSERT_PARENT = "INSERT INTO PARENT(MSISDN, CREATION_DATE) VALUES(?, ?)";
}
