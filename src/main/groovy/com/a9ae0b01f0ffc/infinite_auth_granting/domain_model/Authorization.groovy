package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_http_response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonOutput
import okhttp3.Response

import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_6_util.okhttp_request
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_6_util.process_prerequisite_authorizations

@JsonIgnoreProperties(ignoreUnknown = true)
class Authorization {

    String authorizationName
    Set<Identity> identitySet = new HashSet<Identity>()
    Authorization prerequisiteAuthorization

    static Set<Authorization> create_from_configuration(String i_authorizations_url) {
        ObjectMapper l_object_mapper = new ObjectMapper()
        Set<Authorization> l_authorization_set = new HashSet<Authorization>()
        T_http_response l_response = okhttp_request(i_authorizations_url)
        def (Object l_authorization_set_json, Response l_authorization_set_response, String l_authorization_set_response_body) = okhttp_request(i_authorizations_url)
        for (l_authorization_json in l_authorization_set_json?._embedded?.authorizations) {
            Authorization l_authorization = l_object_mapper.readValue(JsonOutput.toJson(l_authorization_json), Authorization.class)
            process_prerequisite_authorizations(l_authorization_json?._links?.prerequisiteAuthorization?.href, l_authorization)
            l_authorization.getIdentitySet().addAll(Identity.create_from_configuration(l_authorization_json?._links?.identitySet?.href))
            l_authorization_set.add(l_authorization)
        }
        return l_authorization_set
    }

}