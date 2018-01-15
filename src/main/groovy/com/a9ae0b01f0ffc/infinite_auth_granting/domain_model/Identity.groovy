package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Identity {

    String resourceName = this.getClass().getSimpleName()
    String identityName

    Set<Authentication> authenticationSet = new HashSet<Authentication>()

}