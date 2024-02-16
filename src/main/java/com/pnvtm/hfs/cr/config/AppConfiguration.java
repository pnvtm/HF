package com.pnvtm.hfs.cr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
class AppConfiguration implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(
            List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new EventDataArgumentResolver());
    }

}
