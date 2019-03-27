package com.bercut.sa.parentalctl.logs;

/**
 * Created by haimin-a on 25.03.2019.
 */
public enum LoggerText {
    REST_REQUEST("Session {} -> {} request received.\nInput parameters: \n{}"),
    SQL_RESPONSE("Session {} -> {} exec time (ms): {}"),
    SQL_ERROR("Session {} -> {} generate SQL error: {}"),
    REST_ERROR("Session {} -> {} rest response: {}"),
    SQL_REQUEST("Session{} -> {} prepare SQL: \n{} with params: \n{}");

    private String text;

    LoggerText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}