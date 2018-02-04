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

    @Value('${infinite.auth.configuration.relativeUrls.authorizations.search.findByScopeAndAuthorizationType}')
    public String infiniteAuthConfigurationRelativeUrlsAuthorizationsSearchFindByScopeAndAuthorizationType

    @Value('${infinite.auth.configuration.relativeUrls.authorizations.search.matchAuthorizations}')
    public String matchAuthorizations

    @Value('${infinite.auth.configuration.relativeUrls.accessors.search.matchAccessors}')
    public String matchAccessors

    @Value('${infinite.auth.configuration.relativeUrls.authorizations.search.findByScopeAndAuthorizationTypeAndAccessor}')
    public String infiniteAuthConfigurationRelativeUrlsAuthorizationsSearchFindByScopeAndAuthorizationTypeAndAccessor

    @Value('${infinite.auth.configuration.relativeUrls.authorizations.search.findByScopeAndAuthorizationTypeAndAccessorAndIdentity}')
    public String infiniteAuthConfigurationRelativeUrlsAuthorizationsSearchFindByScopeAndAuthorizationTypeAndAccessorAndIdentity

    @Value('${infinite.auth.configuration.relativeUrls.authorizations.search.findByScopeAndAuthorizationTypeAndDefaultAccessor}')
    public String infiniteAuthConfigurationRelativeUrlsAuthorizationsSearchFindByScopeAndAuthorizationTypeAndDefaultAccessor


    @Value('${infinite.auth.configuration.relativeUrls.authorizations.search.findByScopeAndAuthorizationTypeAndDefaultAccessorAndIdentity}')
    public String infiniteAuthConfigurationRelativeUrlsAuthorizationsSearchFindByScopeAndAuthorizationTypeAndDefaultAccessorAndIdentity

    @Value('${infinite.auth.configuration.relativeUrls.accessors}')
    public String infiniteAuthConfigurationRelativeUrlsAccessors


    @Value('${infinite.auth.granting.jwt.keystore.path}')
    public String infiniteAuthGrantingJwtKeystorePath

    @Value('${infinite.auth.granting.jwt.keystore.type}')
    public String infiniteAuthGrantingJwtKeystoreType

    @Value('${infinite.auth.granting.jwt.keystore.password}')
    public String infiniteAuthGrantingJwtKeystorePassword

    @Value('${infinite.auth.granting.jwt.keystore.alias}')
    public String infiniteAuthGrantingJwtKeystoreAlias

    @PostConstruct
    void init() {
        System.out.println("================== " + authenticationModulesPath + "================== ");
    }

}
