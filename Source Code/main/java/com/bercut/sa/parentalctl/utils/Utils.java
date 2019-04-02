package com.bercut.sa.parentalctl.utils;

import com.bercut.sa.parentalctl.db.DbException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

/**
 * Created by haimin-a on 22.03.2019.
 */
public class Utils {

    public static String createUuid() {
        return UUID.randomUUID().toString();
    }

    public static void validateMsisdn(String... msisdns) throws ValidateException {
        for (String msisdn : msisdns) {
            if (msisdn == null || !msisdn.startsWith("7") || msisdn.length() != 11)
                throw new ValidateException("Wrong msisdn");
        }
    }

    public static void validateFlags(Boolean... booleans) throws ValidateException {
        for (Boolean bool : booleans) {
            if (bool == null)
                throw new ValidateException("Wrong params");
        }
    }

    public static HttpStatus parseError(DbException e) {
        switch (e.getErrorCode()) {
            case 20100:
                return HttpStatus.NOT_ACCEPTABLE; //406
            case 1://20101:
                return HttpStatus.CONFLICT; //409
            case 20103:
                return HttpStatus.NOT_FOUND; //404
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR; //500
        }
    }
}
