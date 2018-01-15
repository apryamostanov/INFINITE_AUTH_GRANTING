package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_client_response
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_resource_set
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonOutput

import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.GC_IS_CACHED_NO
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_6_util.okhttp_request

@JsonIgnoreProperties(ignoreUnknown = true)
class Authentication {

    String resourceName = this.getClass().getSimpleName()
    String resourceUrl
    String cacheUrl
    Boolean isCached = GC_IS_CACHED_NO
    String authenticationName

    T_resource_set<DataField> publicDataFieldSet

    T_resource_set<DataField> privateDataFieldSet

    T_resource_set<DataField> keyFieldSet

    T_resource_set<DataField> functionalFieldSet

    String authenticationStatus

/*
    Boolean validate(List<DataField> publicDataFieldList, List<DataField> privateDataFieldList, List<DataField> functionalDataFieldList, List<DataField> keyDataFieldList) {
        Binding l_binding = new Binding()
        l_binding.setVariable("i_public_data_field_list", publicDataFieldList)
        l_binding.setVariable("i_private_data_field_list", privateDataFieldList)
        l_binding.setVariable("o_functional_data_field_list", functionalDataFieldList)
        l_binding.setVariable("o_key_data_field_list", keyDataFieldList)
        System.out.println(T_auth_grant_base_5_context.get_app_context().app_conf().authenticationModulesPath + "Z")
        return T_auth_grant_base_5_context.get_app_context().get_authentication_runner().run(authenticationName, l_binding)
    }*/

}