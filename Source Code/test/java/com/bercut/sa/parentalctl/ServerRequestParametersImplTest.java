package com.bercut.sa.parentalctl;

import com.bercut.mb.sdk.Context;
import com.bercut.mb.sdk.Extension;
import com.bercut.mb.sdk.ExternalTransaction;
import com.bercut.mb.sdk.request.ServerRequestParameters;

import javax.transaction.xa.XAException;
import java.sql.SQLException;

/**
 * Created by haimin-a on 29.03.2019.
 */
public class ServerRequestParametersImplTest implements ServerRequestParameters {
    @Override
    public Extension[] getExtensions() {
        return new Extension[0];
    }

    @Override
    public Context getContext() {
        return new ContextImplTest();
    }

    @Override
    public ExternalTransaction getTransaction() throws XAException, SQLException {
        return null;
    }
}
