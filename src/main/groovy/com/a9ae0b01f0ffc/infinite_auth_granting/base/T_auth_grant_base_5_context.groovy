package com.a9ae0b01f0ffc.infinite_auth_granting.base

import com.a9ae0b01f0ffc.infinite_auth_granting.client.*
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_accessor_type_repository
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_authentication_type_repository
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_authorization_type_repository
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_grant_type_repository
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_identity_type_repository
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_scope_type_repository
import com.a9ae0b01f0ffc.infinite_auth_granting.server.E_api_exception
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.net.ssl.HostnameVerifier
import java.nio.charset.StandardCharsets
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

@Component
class T_auth_grant_base_5_context extends T_auth_grant_base_4_const {


    GroovyScriptEngine p_authentication_runner
    GroovyScriptEngine p_authentication_config_holder
    OkHttpClient p_ok_http_client = new OkHttpClient.Builder().hostnameVerifier(get_unsecure_host_name_verifier()).build()

    @JsonIgnore
    @Autowired
    ObjectMapper p_object_mapper

    @Autowired
    T_jwt_manager p_jwt_manager
    @Autowired
    @JsonIgnore
    T_auth_grant_conf p_app_conf

    @Autowired
    @JsonIgnore
    I_accessor_type_repository p_accessor_type_repository
    @Autowired
    @JsonIgnore
    I_authentication_type_repository p_authentication_repository
    @Autowired
    @JsonIgnore
    I_identity_type_repository p_identity_repository
    @Autowired
    @JsonIgnore
    I_scope_type_repository p_scope_repository
    @Autowired
    @JsonIgnore
    I_grant_type_repository p_grant_repository
    @Autowired
    @JsonIgnore
    I_authorization_type_repository p_authorization_type_repository

    static HostnameVerifier get_unsecure_host_name_verifier() {
        return new T_host_name_verifier()
    }

    GroovyScriptEngine get_authentication_runner() {
        if (is_null(p_authentication_runner)) {
            p_authentication_runner = new GroovyScriptEngine(app_conf().authenticationModulesPath)
        }
        return p_authentication_runner
    }

    GroovyScriptEngine get_authentication_config_holder() {
        if (is_null(p_authentication_runner)) {
            p_authentication_config_holder = new GroovyScriptEngine(app_conf().authenticationConfigPath)
        }
        return p_authentication_config_holder
    }

    T_auth_grant_conf app_conf() {
        return p_app_conf
    }

    static def zip(String s) {return s
        def targetStream = new ByteArrayOutputStream()
        def zipStream = new GZIPOutputStream(targetStream)
        zipStream.write(s.getBytes(StandardCharsets.UTF_8.name()))
        zipStream.close()
        def zippedBytes = targetStream.toByteArray()
        targetStream.close()
        return zippedBytes.encodeBase64()
    }

    static def unzip(String compressed) {return compressed
        def inflaterStream = new GZIPInputStream(new ByteArrayInputStream(compressed.decodeBase64()))
        def uncompressedStr = inflaterStream.getText(StandardCharsets.UTF_8.name())
        return uncompressedStr
    }

}
