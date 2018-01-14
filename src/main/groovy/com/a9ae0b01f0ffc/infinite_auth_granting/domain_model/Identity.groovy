package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonOutput
import okhttp3.Response

import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_6_util.okhttp_request

@JsonIgnoreProperties(ignoreUnknown = true)
class Identity {

    String identityName

    Set<Authentication> authenticationSet = new HashSet<Authentication>()

    static Set<Identity> create_from_configuration(String i_identity_set_url) {
        ObjectMapper l_object_mapper = new ObjectMapper()
        Set<Identity> l_identity_set = new HashSet<Identity>()
        def (Object l_identity_set_json, Response l_identity_set_response, String l_identity_set_response_body) = okhttp_request(i_identity_set_url)
        for (l_identity_json in l_identity_set_json?._embedded?.identities) {
            Identity l_identity = l_object_mapper.readValue(JsonOutput.toJson(l_identity_json), Identity.class)
            l_identity.getAuthenticationSet().addAll(Authentication.create_from_configuration(l_identity_json?._links?.authenticationSet?.href))
            l_identity_set.add(l_identity)
        }
        return l_identity_set
    }

}