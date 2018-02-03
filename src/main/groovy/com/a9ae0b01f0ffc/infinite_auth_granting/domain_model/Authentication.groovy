package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const
import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_5_context
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_hal_resource
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.GC_STATUS_FAILED
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.GC_STATUS_SUCCESSFUL

@JsonIgnoreProperties(ignoreUnknown = true)
class Authentication extends T_hal_resource {
    String authenticationName

    Map<String, String> publicDataFieldSet

    Map<String, String> privateDataFieldSet

    String authenticationStatus = T_auth_grant_base_4_const.GC_STATUS_NEW

    String errorCode

    void failure() {
        this.authenticationStatus = GC_STATUS_FAILED
    }

    void success() {
        this.authenticationStatus = GC_STATUS_SUCCESSFUL
    }

    void common_authentication_validation(Authentication i_conf_authentication, Map<String, String> o_key_field_map, Map<String, String> o_functional_field_map, Map<String, String> i_functional_field_map, T_auth_grant_base_5_context i_context) {
        if (authenticationName != i_conf_authentication.authenticationName) {
            failure()
            return
        }
        Binding l_binding = new Binding()
        l_binding.setVariable("io_authentication", this)
        l_binding.setVariable("i_context", i_context)
        l_binding.setVariable("o_key_field_map", o_key_field_map)
        l_binding.setVariable("o_functional_field_map", o_functional_field_map)
        l_binding.setVariable("i_functional_field_map", i_functional_field_map)
        i_context.get_authentication_runner().run(authenticationName + i_context.app_conf().authenticationModulesExtension, l_binding)
    }

}