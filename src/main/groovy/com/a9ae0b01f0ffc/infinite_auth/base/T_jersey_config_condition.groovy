package com.a9ae0b01f0ffc.infinite_auth.base

import com.a9ae0b01f0ffc.infinite_auth.granting.Authorization
import com.a9ae0b01f0ffc.infinite_auth.server.ApiExceptionMapper
import com.a9ae0b01f0ffc.infinite_auth.validation.Introspection
import com.a9ae0b01f0ffc.infinite_auth.validation.Validation
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.context.annotation.Conditional
import org.springframework.core.type.AnnotatedTypeMetadata

class T_jersey_config_condition implements Condition {


    @Override
    boolean matches(ConditionContext i_condition_context, AnnotatedTypeMetadata i_annotated_type_metadata){
        System.out.println(i_condition_context.getEnvironment().getProperty("infinite.auth.mode"))
        if (i_condition_context.getEnvironment().getProperty("infinite.auth.mode") == T_auth_grant_base_4_const.GC_MODE_CONFIGURATION) {
            System.out.println("*****************Disabling Jersey services - to enable JPA repositories for configuration")
            return false
        } else {
            System.out.println("*****************Enabling Jersey services - for other modes")
            return true
        }
    }
}