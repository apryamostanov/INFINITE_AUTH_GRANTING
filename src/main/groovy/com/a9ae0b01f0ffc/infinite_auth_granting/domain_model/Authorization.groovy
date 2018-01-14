package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_client_response
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

}