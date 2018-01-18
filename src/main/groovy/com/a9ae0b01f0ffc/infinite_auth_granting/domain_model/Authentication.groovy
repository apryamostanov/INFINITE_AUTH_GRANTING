package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_hal_resource
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Authentication  extends T_hal_resource{
    String authenticationName

    T_hal_resource publicDataFieldSet

    T_hal_resource privateDataFieldSet

    T_hal_resource keyFieldSet

    T_hal_resource functionalFieldSet

    String authenticationStatus = "new"

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