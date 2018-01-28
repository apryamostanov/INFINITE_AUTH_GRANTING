package com.a9ae0b01f0ffc.infinite_auth_granting.base

import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Authorization
import com.a9ae0b01f0ffc.infinite_auth_granting.server.T_authorizations_service
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.stereotype.Component

@Component
class T_jersey_config extends ResourceConfig {

    T_jersey_config() {
        registerEndpoints()
    }
    private void registerEndpoints() {
        register(T_authorizations_service.class)
    }

}