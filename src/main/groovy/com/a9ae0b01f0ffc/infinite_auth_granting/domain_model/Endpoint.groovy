package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper

import static base.T_common_base_1_const.GC_EMPTY_STRING
import static base.T_common_base_1_const.GC_NULL_OBJ_REF
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_6_util.okhttp_request

@JsonIgnoreProperties(ignoreUnknown = true)
class Endpoint {

    String endpointName = GC_EMPTY_STRING
    Version apiVersion = GC_NULL_OBJ_REF as Version

    static Endpoint create_from_configuration(String i_endpoint_href) {
        def (Object l_endpoint_json, Object l_endpoint_response, String l_endpoint_response_body) = okhttp_request(i_endpoint_href)
        ObjectMapper l_object_mapper = new ObjectMapper()
        Endpoint l_endpoint = l_object_mapper.readValue(l_endpoint_response_body, Endpoint.class)
        l_endpoint.setApiVersion(Version.create_from_configuration(l_endpoint_json?._links?.apiVersion?.href))
        return l_endpoint
    }

}