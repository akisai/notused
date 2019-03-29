package com.bercut.sa.parentalctl;

import com.bercut.mb.sdk.*;

import java.util.UUID;

/**
 * Created by haimin-a on 29.03.2019.
 */
public class ContextImplTest implements Context {
    @Override
    public UUID getUuid() {
        return UUID.randomUUID();
    }

    @Override
    public Session getSession(PartnerLinkTypeInfo partnerLinkTypeInfo) {
        return null;
    }

    @Override
    public PLTContext getPLTContext(PartnerLinkTypeInfo partnerLinkTypeInfo) {
        return null;
    }

    @Override
    public PrincipalContext getPrincipalContext() {
        return null;
    }

    @Override
    public void setAttachment(Object o) {

    }

    @Override
    public Object getAttachment() {
        return null;
    }

    @Override
    public void addSessionLifecycleListener(SessionLifecycleListener sessionLifecycleListener) {

    }

    @Override
    public void removeSessionLifecycleListener(SessionLifecycleListener sessionLifecycleListener) {

    }

    @Override
    public void removeSessionLifecycleListeners() {

    }
}
