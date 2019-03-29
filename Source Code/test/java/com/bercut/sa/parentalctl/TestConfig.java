package com.bercut.sa.parentalctl;

import com.bercut.sa.parentalctl.atlas.AtlasProvider;
import com.bercut.sa.parentalctl.atlas.AtlasProviderImplTest;
import com.bercut.sa.parentalctl.db.DbService;
import com.bercut.sa.parentalctl.db.DbServiceImplTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by haimin-a on 29.03.2019.
 */
@Configuration
@ComponentScan(basePackages = "com.bercut.sa.parentalctl.rest")
public class TestConfig {

    @Bean
    AtlasProvider atlasProvider() {
        return new AtlasProviderImplTest();
    }

    @Bean
    DbService dbService() {
        return new DbServiceImplTest();
    }
}
