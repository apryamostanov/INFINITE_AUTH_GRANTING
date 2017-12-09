package com.a9ae0b01f0ffc.infinite_auth_granting.base

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import other.T_thread_local

import javax.annotation.PostConstruct

@Component
class T_auth_base_5_context extends T_auth_base_4_const {

    static T_thread_local<T_auth_base_5_context> p_auth_base_5_context_thread_local = new T_thread_local<T_auth_base_5_context>()
    @Autowired
    T_auth_conf p_app_config
    GroovyScriptEngine p_authentication_runner = GC_NULL_OBJ_REF as GroovyScriptEngine

    T_auth_base_5_context() {
        p_auth_base_5_context_thread_local.set(this)
    }

    @PostConstruct
    static init_standalone() {
        get_app_context().p_authentication_runner = new GroovyScriptEngine(get_app_context().p_app_config.authenticationModulesPath)
    }

    static T_auth_base_5_context get_app_context() {
        return p_auth_base_5_context_thread_local.get(T_auth_base_5_context.class) as T_auth_base_5_context
    }


    T_auth_conf app_conf() {
        return p_app_config
    }


    GroovyScriptEngine get_authentication_runner() {
        return p_authentication_runner
    }
}
