package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_http_response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonOutput
import okhttp3.Response

import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_6_util.okhttp_request

@JsonIgnoreProperties(ignoreUnknown = true)
class Authentication {

    String authenticationName

    static Set<Authentication> create_from_configuration(String i_authentication_set_url) {
        ObjectMapper l_object_mapper = new ObjectMapper()
        Set<Authentication> l_authentication_set = new HashSet<Authentication>()
        T_http_response l_response = okhttp_request(i_authentication_set_url)
        for (l_authentication_json in l_response.p_slurped_response_json?._embedded?.authentications) {
            Authentication l_authentication = l_object_mapper.readValue(JsonOutput.toJson(l_authentication_json), Authentication.class)
            l_authentication_set.add(l_authentication)
        }
        return l_authentication_set
    }

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