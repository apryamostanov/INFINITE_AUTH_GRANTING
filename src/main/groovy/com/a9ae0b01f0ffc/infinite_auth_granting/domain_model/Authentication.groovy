package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const
import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_5_context
import com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model.AuthenticationType
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import static base.T_common_base_1_const.GC_NULL_OBJ_REF
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.GC_STATUS_FAILED
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.GC_STATUS_SUCCESSFUL

@JsonIgnoreProperties(ignoreUnknown = true)
class Authentication {
    String authenticationName

    @JsonProperty("public_data")
    HashMap<String, String> publicDataFieldSet

    @JsonProperty("private_data")
    HashMap<String, String> privateDataFieldSet

    @JsonIgnore
    Authorization p_parent_authorization

    @JsonIgnore
    HashMap<String, String> keyFieldMap

    @JsonIgnore
    HashMap<String, String> functionalFieldMap

    @JsonProperty("status")
    String authenticationStatus = T_auth_grant_base_4_const.GC_STATUS_NEW

    @JsonIgnore
    def p_conf

    @JsonIgnore
    T_auth_grant_base_5_context p_context

    void failure() {
        this.authenticationStatus = GC_STATUS_FAILED
    }

    void success() {
        this.authenticationStatus = GC_STATUS_SUCCESSFUL
    }

    void common_authentication_validation(AuthenticationType i_conf_authentication, T_auth_grant_base_5_context i_context, Authorization i_parent_authorization) {
        if (authenticationName != i_conf_authentication.authenticationName) {
            failure()
            return
        }
        p_context = i_context
        p_parent_authorization = i_parent_authorization
        p_conf = p_context.get_authentication_config_holder().run(i_context.app_conf().authenticationConfigFileName + i_context.app_conf().authenticationModulesExtension, new Binding())
        Binding l_binding = new Binding()
        l_binding.setVariable("io_user_authentication", this)
        i_context.get_authentication_runner().run(authenticationName + i_context.app_conf().authenticationModulesExtension, l_binding)
        privateDataFieldSet = GC_NULL_OBJ_REF as HashMap<String, String>
    }

}