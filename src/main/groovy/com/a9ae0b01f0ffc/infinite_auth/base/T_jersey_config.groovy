package com.a9ae0b01f0ffc.infinite_auth.base

import com.a9ae0b01f0ffc.infinite_auth.granting.Authorization
import com.a9ae0b01f0ffc.infinite_auth.server.ApiExceptionMapper
import com.a9ae0b01f0ffc.infinite_auth.validation.Introspection
import com.a9ae0b01f0ffc.infinite_auth.validation.Validation
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.context.annotation.Conditional
import org.springframework.stereotype.Component

@Conditional(T_jersey_config_condition.class)
@Component
class T_jersey_config extends ResourceConfig {

    T_jersey_config() {
        registerEndpoints()
    }

    void registerEndpoints() {
        register(Authorization.class)
        register(Introspection.class)
        register(Validation.class)
        register(ApiExceptionMapper.class)
    }

}