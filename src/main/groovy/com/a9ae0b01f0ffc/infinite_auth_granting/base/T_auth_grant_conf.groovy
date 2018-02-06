package com.a9ae0b01f0ffc.infinite_auth_granting.base

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@Component
@PropertySource(value = "application.properties")
class T_auth_grant_conf {

    @Value('${authenticationModulesPath}')
    public String authenticationModulesPath

    @Value('${authenticationConfigPath}')
    public String authenticationConfigPath

    @Value('${authenticationConfigFileName}')
    public String authenticationConfigFileName

    @Value('${authenticationModulesExtension}')
    public String authenticationModulesExtension

    @Value('${infinite.auth.configuration.baseUrl}')
    public String infiniteAuthConfigurationBaseUrl

    @Value('${infinite.auth.configuration.relativeUrls.scopes.search.findByScopeName}')
    public String infiniteAuthConfigurationRelativeUrlsScopesSearchFindByScopeName

    @Value('${infinite.auth.configuration.relativeUrls.scopes.search.findByScopeNameAndAccessor}')
    public String infiniteAuthConfigurationRelativeUrlsScopesSearchFindByScopeNameAndAccessor

    @Value('${infinite.auth.configuration.relativeUrls.identities.search.findByIdentityName}')
    public String infiniteAuthConfigurationRelativeUrlsIdentitiesSearchFindByIdentityName

    @Value('${infinite.auth.configuration.relativeUrls.authorizations.search.matchAuthorizations}')
    public String matchAuthorizations

    @Value('${infinite.auth.configuration.relativeUrls.accessors.search.matchAccessors}')
    public String matchAccessors

    @Value('${infinite.auth.configuration.relativeUrls.accessors}')
    public String infiniteAuthConfigurationRelativeUrlsAccessors


    @Value('${infinite.auth.granting.jwt.access.keystore.path}')
    public String infiniteAuthGrantingJwtAccessKeystorePath

    @Value('${infinite.auth.granting.jwt.access.keystore.type}')
    public String infiniteAuthGrantingJwtAccessKeystoreType

    @Value('${infinite.auth.granting.jwt.access.keystore.password}')
    public String infiniteAuthGrantingJwtAccessKeystorePassword

    @Value('${infinite.auth.granting.jwt.access.keystore.alias}')
    public String infiniteAuthGrantingJwtAccessKeystoreAlias

    @Value('${infinite.auth.granting.jwt.refresh.keystore.path}')
    public String infiniteAuthGrantingJwtRefreshKeystorePath

    @Value('${infinite.auth.granting.jwt.refresh.keystore.type}')
    public String infiniteAuthGrantingJwtRefreshKeystoreType

    @Value('${infinite.auth.granting.jwt.refresh.keystore.password}')
    public String infiniteAuthGrantingJwtRefreshKeystorePassword

    @Value('${infinite.auth.granting.jwt.refresh.keystore.alias}')
    public String infiniteAuthGrantingJwtRefreshKeystoreAlias

    @PostConstruct
    void init() {
        System.out.println("================== " + authenticationModulesPath + "================== ");
    }

}
