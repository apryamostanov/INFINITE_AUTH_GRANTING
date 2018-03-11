package com.a9ae0b01f0ffc.infinite_auth.base

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct
import java.security.Key
import java.security.KeyStore

@Component
class T_jwt_manager {

    protected Key p_jwt_access_key = T_auth_grant_base_4_const.GC_NULL_OBJ_REF as Key
    protected Key p_jwt_refresh_key = T_auth_grant_base_4_const.GC_NULL_OBJ_REF as Key

    @Autowired
    @JsonIgnore
    T_auth_grant_conf p_app_conf

    Key get_jwt_access_key() {
        return p_jwt_access_key
    }

    Key get_jwt_refresh_key() {
        return p_jwt_refresh_key
    }

    @PostConstruct
    void init() {
        FileInputStream l_access_keystore_file_input_stream = new FileInputStream(p_app_conf.infiniteAuthJwtAccessKeystorePath)
        KeyStore l_access_key_store = KeyStore.getInstance(p_app_conf.infiniteAuthJwtAccessKeystoreType)
        l_access_key_store.load(l_access_keystore_file_input_stream, p_app_conf.infiniteAuthJwtAccessKeystorePassword.toCharArray())
        p_jwt_access_key = l_access_key_store.getKey(p_app_conf.infiniteAuthJwtAccessKeystoreAlias, p_app_conf.infiniteAuthJwtAccessKeystorePassword.toCharArray())

        FileInputStream l_refresh_keystore_file_input_stream = new FileInputStream(p_app_conf.infiniteAuthJwtRefreshKeystorePath)
        KeyStore l_refresh_key_store = KeyStore.getInstance(p_app_conf.infiniteAuthJwtRefreshKeystoreType)
        l_refresh_key_store.load(l_refresh_keystore_file_input_stream, p_app_conf.infiniteAuthJwtRefreshKeystorePassword.toCharArray())
        p_jwt_refresh_key = l_refresh_key_store.getKey(p_app_conf.infiniteAuthJwtRefreshKeystoreAlias, p_app_conf.infiniteAuthJwtRefreshKeystorePassword.toCharArray())
    }

}
