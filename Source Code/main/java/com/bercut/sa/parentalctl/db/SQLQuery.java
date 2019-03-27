package com.bercut.sa.parentalctl.db;

/**
 * Created by haimin-a on 26.03.2019.
 */
public class SQLQuery {
    static String GET_PARENT = "SELECT ID, MSISDN FROM PARENT WHERE MSISDN = ?";
    static String GET_CHILD = "SELECT MSISDN FROM CHILD WHERE MSISDN = ?";
    static String GET_CHILD_BY_PID = "SELECT C.MSISDN FROM CHILD C JOIN PARENT P ON C.PARENT_ID = P.ID WHERE P.MSISDN = ? AND ROWNUM = 1";
    static String INSERT_PARENT = "INSERT INTO PARENT(MSISDN) VALUES(?)";
    static String INSERT_CHILD = "INSERT INTO CHILD(MSISDN, PARENT_ID, FWD_AOC, FWD_PAY) VALUES(?, ?, ?, ?)";
    static String DELETE_PARENT = "DELETE FROM PARENT WHERE MSISDN = ?";
    static String DELETE_CHILD = "DELETE FROM CHILD WHERE MSISDN = ?";
    static String UPDATE_CHILD = "UPDATE CHILD SET FWD_AOC = ?, FWD_PAY = ? WHERE MSISDN = ?";
    static String GET_FULL_CHILD = "SELECT C.MSISDN, P.MSISDN AS PARENT, C.FWD_AOC, C.FWD_PAY FROM CHILD C JOIN PARENT P ON C.PARENT_ID = P.ID WHERE C.MSISDN = ?";
    public static String GET_ALL_PARENT = "SELECT ID, MSISDN FROM PARENT";
    public static String GET_ALL_CHILDREN = "SELECT C.MSISDN, P.MSISDN AS PARENT, C.FWD_AOC, C.FWD_PAY FROM CHILD C JOIN PARENT P ON C.PARENT_ID = P.ID";
}
