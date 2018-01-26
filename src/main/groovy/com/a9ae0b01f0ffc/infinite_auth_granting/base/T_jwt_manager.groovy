package com.a9ae0b01f0ffc.infinite_auth_granting.base

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct
import java.security.Key
import java.security.KeyStore

@Component
class T_jwt_manager {

    protected Key p_jwt_key = T_auth_grant_base_4_const.GC_NULL_OBJ_REF as Key

    @Autowired
    @JsonIgnore
    T_auth_grant_conf p_app_conf

    Key get_jwt_key() {
        return p_jwt_key
    }

    @PostConstruct
    void init() {
        FileInputStream l_keystore_file_input_stream = new FileInputStream(p_app_conf.infiniteAuthGrantingJwtKeystorePath)
        KeyStore l_key_store = KeyStore.getInstance(p_app_conf.infiniteAuthGrantingJwtKeystoreType)
        l_key_store.load(l_keystore_file_input_stream, p_app_conf.infiniteAuthGrantingJwtKeystorePassword.toCharArray())
        p_jwt_key = l_key_store.getKey(p_app_conf.infiniteAuthGrantingJwtKeystoreAlias, p_app_conf.infiniteAuthGrantingJwtKeystorePassword.toCharArray())
    }

}
