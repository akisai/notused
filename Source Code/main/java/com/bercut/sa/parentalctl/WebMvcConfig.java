package com.bercut.sa.parentalctl;

import com.bercut.sa.parentalctl.atlas.AtlasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by haimin-a on 09.07.2019.
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    private final AtlasProvider atlasProvider;

    @Autowired
    public WebMvcConfig(AtlasProvider atlasProvider) {
        this.atlasProvider = atlasProvider;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IPInterceptor(atlasProvider)).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
