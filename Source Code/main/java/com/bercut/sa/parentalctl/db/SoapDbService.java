package com.bercut.sa.parentalctl.db;

import com.bercut.schema.aoi_parentalctl.GetMsisdnRequestType;
import com.bercut.schema.aoi_parentalctl.GetMsisdnResponseType;

/**
 * Created by haimin-a on 25.03.2019.
 */
public interface SoapDbService {

    GetMsisdnResponseType getMsisdn(String sessionId, GetMsisdnRequestType msisdn);
}
