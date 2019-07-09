package com.bercut.sa.parentalctl.atlas;

import java.util.List;

/**
 * Created by haimin-a on 22.03.2019.
 */
public interface AtlasProvider {
    String getDataSource();
    List<String> getAllowedIps();
}
