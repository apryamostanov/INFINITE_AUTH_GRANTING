package com.a9ae0b01f0ffc.infinite_auth.base

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

    @Value('${infinite.auth.jwt.access.keystore.path}')
    public String infiniteAuthJwtAccessKeystorePath

    @Value('${infinite.auth.jwt.access.keystore.type}')
    public String infiniteAuthJwtAccessKeystoreType

    @Value('${infinite.auth.jwt.access.keystore.password}')
    public String infiniteAuthJwtAccessKeystorePassword

    @Value('${infinite.auth.jwt.access.keystore.alias}')
    public String infiniteAuthJwtAccessKeystoreAlias

    @Value('${infinite.auth.jwt.refresh.keystore.path}')
    public String infiniteAuthJwtRefreshKeystorePath

    @Value('${infinite.auth.jwt.refresh.keystore.type}')
    public String infiniteAuthJwtRefreshKeystoreType

    @Value('${infinite.auth.jwt.refresh.keystore.password}')
    public String infiniteAuthJwtRefreshKeystorePassword

    @Value('${infinite.auth.jwt.refresh.keystore.alias}')
    public String infiniteAuthJwtRefreshKeystoreAlias

    @Value('${granting_endpoint_name}')
    public String granting_endpoint_name

    @PostConstruct
    void init() {
        System.out.println("================== " + authenticationModulesPath + "================== ");
    }

}
