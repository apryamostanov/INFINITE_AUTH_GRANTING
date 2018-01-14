package com.a9ae0b01f0ffc.infinite_auth_granting.base

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@Component
@PropertySource(value = "application.properties")
class T_auth_grant_conf {

    @Value('${authenticationModulesPath}')
    public String authenticationModulesPath

    @Value('${infinite.auth.configuration.baseUrl}')
    public String infiniteAuthConfigurationBaseUrl

    @Value('${infinite.auth.configuration.relativeUrls.scopes.search.findByScopeName}')
    public String infiniteAuthConfigurationRelativeUrlsScopesSearchFindByScopeName

    @Value('infinite.auth.configuration.relativeUrls.authorizations.search.findByAuthorizationName}')
    public String infiniteAuthConfigurationRelativeUrlsAuthorizationsFindByAuthorizationName

    @PostConstruct
    void init() {
        System.out.println("================== " + authenticationModulesPath + "================== ");
    }

}