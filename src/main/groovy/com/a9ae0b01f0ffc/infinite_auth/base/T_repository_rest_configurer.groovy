package com.a9ae0b01f0ffc.infinite_auth.base

import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter

import java.util.concurrent.TimeUnit

@Configuration
class T_repository_rest_configurer extends RepositoryRestConfigurerAdapter {

    @Override
    void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.getCorsRegistry()
                .addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
                .allowedHeaders("*")
                .exposedHeaders("WWW-Authenticate")
                .allowCredentials(true)
                .maxAge(TimeUnit.DAYS.toSeconds(1))
    }

}