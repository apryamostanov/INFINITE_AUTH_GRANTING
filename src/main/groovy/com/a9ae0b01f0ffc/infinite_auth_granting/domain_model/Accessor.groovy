package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_http_response
import com.a9ae0b01f0ffc.infinite_auth_granting.server.E_api_exception
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper

import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_6_util.*

@JsonIgnoreProperties(ignoreUnknown = true)
class Accessor {

    String appName
    String accessorName
    String platform
    String appVersion
    String user
    String FIID
    String product
    String productGroup
    Endpoint endpoint
    Version apiVersion
    Accessor parentAccessor

    static Accessor create_from_configuration(String i_accessor_url) {
        ObjectMapper l_object_mapper = new ObjectMapper()
        T_http_response l_response = okhttp_request(i_accessor_url)
        Accessor l_accessor = l_object_mapper.readValue(l_response.p_response_string, Accessor.class)
        l_accessor.setApiVersion(Version.create_from_configuration(l_response.p_slurped_response_json?._links?.apiVersion?.href))
        try {
            l_accessor.setEndpoint(Endpoint.create_from_configuration(l_response.p_slurped_response_json?._links?.endpoint?.href))
        } catch (E_api_exception e_api_exception) {
            if (e_api_exception.get_code() != HttpURLConnection.HTTP_NOT_FOUND) {
                throw e_api_exception
            }
        }
        process_parent_accessors(l_response.p_slurped_response_json?._links?.parentAccessor?.href, l_accessor)
        return l_accessor
    }

}