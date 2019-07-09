package com.bercut.sa.parentalctl.atlas;

import java.util.List;

/**
 * Created by haimin-a on 29.03.2019.
 */
public class AtlasProviderImplTest implements AtlasProvider {
    @Override
    public String getDataSource() {
        return "test";
    }

    @Override
    public List<String> getAllowedIps() {
        return null;
    }
}
