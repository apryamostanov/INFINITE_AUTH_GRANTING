package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper

import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_6_util.okhttp_request

@JsonIgnoreProperties(ignoreUnknown = true)
class Scope {

    String scopeName
    Accessor accessor
    Set<Authorization> authorizationSet = new HashSet<Authorization>()


    Set<Grant> grantSet = new HashSet<Grant>()

}