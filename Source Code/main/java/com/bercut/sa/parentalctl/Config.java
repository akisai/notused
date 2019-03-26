package com.bercut.sa.parentalctl;

import com.bercut.sa.parentalctl.atlas.AtlasProviderImpl;
import com.bercut.sa.parentalctl.db.DbService;
import com.bercut.sa.parentalctl.db.DbServiceImpl;
import com.bercut.sa.parentalctl.rest.ParentalctlRestController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Created by haimin-a on 22.03.2019.
 */
@Configuration
@ComponentScan(basePackages = "com.bercut.sa.parentalctl")
public class Config {

    private AtlasProviderImpl atlasProvider;

    @Bean
    @Order(value = Ordered.HIGHEST_PRECEDENCE)
    public AtlasProviderImpl atlasFirst() {
        this.atlasProvider = new AtlasProviderImpl();
        return atlasProvider;
    }

}
