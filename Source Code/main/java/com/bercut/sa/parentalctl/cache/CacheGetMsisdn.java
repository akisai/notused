package com.bercut.sa.parentalctl.cache;

import com.bercut.sa.parentalctl.db.SQLQuery;
import com.bercut.sa.parentalctl.utils.SqlUtils;
import com.bercut.schema.aoi_parentalctl.GetMsisdnResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by haimin-a on 27.03.2019.
 */
@Component
@EnableScheduling
public class CacheGetMsisdn {

    private final SqlUtils sqlUtils;
    private final Logger logger = LoggerFactory.getLogger(CacheGetMsisdn.class);

    private HashMap<String, GetMsisdnResponseType> getMsisdnCache = new HashMap<>();
    private HashMap<String, GetMsisdnResponseType> tempCache = new HashMap<>();

    @Autowired
    public CacheGetMsisdn(SqlUtils sqlUtils) {
        this.sqlUtils = sqlUtils;
    }

    public HashMap<String, GetMsisdnResponseType> getGetMsisdnCache() {
        return getMsisdnCache;
    }

    @Scheduled(initialDelay = 1000, fixedRate = 3600000)
    private void updateBaseCache() {
        logger.warn("Updating base cache");
        try ( Connection conn = sqlUtils.getConnection() ) {
            try ( PreparedStatement psGetAllParent = conn.prepareStatement(SQLQuery.GET_ALL_PARENT) ) {
                try ( ResultSet rsGetAllParent = psGetAllParent.executeQuery() ) {
                    while (rsGetAllParent.next()) {
                        String pMsisdn = rsGetAllParent.getString("MSISDN");
                        GetMsisdnResponseType response = new GetMsisdnResponseType();
                        response.setResult("ok_parent");
                        response.setMsisdn(pMsisdn);
                        tempCache.put(pMsisdn, response);
                    }
                }
            }
            try ( PreparedStatement psGetAllChild = conn.prepareStatement(SQLQuery.GET_ALL_CHILDREN) ) {
                try ( ResultSet rsGetFullChild = psGetAllChild.executeQuery() ) {
                    while (rsGetFullChild.next()) {
                        String cMsisdn = rsGetFullChild.getString("MSISDN");
                        GetMsisdnResponseType response = new GetMsisdnResponseType();
                        response.setResult("ok_children");
                        response.setMsisdn(cMsisdn);
                        response.setParent(rsGetFullChild.getString("PARENT"));
                        response.setFwdAoc(rsGetFullChild.getInt("FWD_AOC") == 1);
                        response.setFwdPay(rsGetFullChild.getInt("FWD_PAY") == 1);
                        tempCache.put(cMsisdn,response);
                    }
                }
            }
            getMsisdnCache.clear();
            getMsisdnCache.putAll(tempCache);
            tempCache.clear();
        } catch (SQLException e) {
            logger.error("Error update base cache");
        }
        logger.warn("Updating cache done, size: " + getMsisdnCache.size());
    }
}
