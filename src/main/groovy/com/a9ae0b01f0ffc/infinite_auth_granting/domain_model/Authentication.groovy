package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const
import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_5_context
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_hal_resource
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_resource_set
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.GC_AUTHENTICATION_ERROR_CODE_04_WRONG_NAME
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.GC_STATUS_FAILED
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.GC_STATUS_SUCCESSFUL

@JsonIgnoreProperties(ignoreUnknown = true)
class Authentication extends T_hal_resource {
    String authenticationName

    T_resource_set<Field> publicDataFieldSet

    T_resource_set<Field> privateDataFieldSet

    T_resource_set<Field> keyFieldSet

    T_resource_set<Field> functionalFieldSet

    String authenticationStatus = T_auth_grant_base_4_const.GC_STATUS_NEW

    String errorCode

    void failure() {
        this.authenticationStatus = GC_STATUS_FAILED
    }

    void success() {
        this.authenticationStatus = GC_STATUS_SUCCESSFUL
    }

    void common_authentication_validation(Authentication i_conf_authentication, T_auth_grant_base_5_context i_context) {
        if (authenticationName != i_conf_authentication.authenticationName) {
            failure()
            return
        }
        Binding l_binding = new Binding()
        l_binding.setVariable("io_authentication", this)
        l_binding.setVariable("i_context", i_context)
        i_context.get_authentication_runner().run(authenticationName + i_context.app_conf().authenticationModulesExtension, l_binding)
    }

}