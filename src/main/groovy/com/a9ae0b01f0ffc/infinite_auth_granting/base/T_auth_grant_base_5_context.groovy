package com.a9ae0b01f0ffc.infinite_auth_granting.base

import com.fasterxml.jackson.annotation.JsonIgnore
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.stereotype.Component
import other.T_thread_local

import javax.net.ssl.HostnameVerifier

@Component
class T_auth_grant_base_5_context extends T_auth_grant_base_4_const {

    static T_thread_local<T_auth_grant_base_5_context> p_auth_base_5_context_thread_local = new T_thread_local<T_auth_grant_base_5_context>()
    GroovyScriptEngine p_authentication_runner = null
    OkHttpClient p_ok_http_client = new OkHttpClient.Builder().hostnameVerifier(get_unsecure_host_name_verifier()).build()
    @Autowired
    @JsonIgnore
    T_auth_grant_conf p_app_conf

    static HostnameVerifier get_unsecure_host_name_verifier() {
        return new T_host_name_verifier()
    }

    T_auth_grant_base_5_context() {
        p_auth_base_5_context_thread_local.set(this)
    }

    static T_auth_grant_base_5_context get_app_context() {
        if (is_null(p_auth_base_5_context_thread_local.get(T_auth_grant_base_5_context.class))) {
            p_auth_base_5_context_thread_local.set(new T_auth_grant_base_5_context())

        }
        return p_auth_base_5_context_thread_local.get(T_auth_grant_base_5_context.class) as T_auth_grant_base_5_context
    }

    GroovyScriptEngine get_authentication_runner() {
        return p_authentication_runner
    }

    T_auth_grant_conf app_conf() {
        return p_app_conf
    }

}
