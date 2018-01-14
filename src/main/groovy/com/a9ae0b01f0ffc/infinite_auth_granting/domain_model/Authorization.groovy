package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Authorization {

    String authorizationName
    Set<Identity> identitySet = new HashSet<Identity>()
    Authorization prerequisiteAuthorization
    String authorizationStatus
    Accessor accessor

}