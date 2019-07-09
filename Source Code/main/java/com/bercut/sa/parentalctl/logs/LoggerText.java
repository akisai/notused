package com.bercut.sa.parentalctl.logs;

/**
 * Created by haimin-a on 25.03.2019.
 */
public enum LoggerText {
    REST_REQUEST("Session {} -> {} request received.\nInput parameters: \n{}"),
    SQL_RESPONSE("Session {} -> {} exec time: {} ms"),
    DB_ERROR("Session {} -> {} generate SQL error: {}"),
    REST_ERROR("Session {} -> {} rest response: {}"),
    VALIDATE_ERROR("Session {} -> {} wrong request: {}"),
    SQL_REQUEST("Session {} -> {} prepare SQL: \n{} with params: \n{}"),
    SQL_OPERATION("Session {} -> {} done"),
    IP_DENIED("User IpAdress denied -> {}"),
    COMPLETE("Complete request in -> {} ms");

    private String text;

    LoggerText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
