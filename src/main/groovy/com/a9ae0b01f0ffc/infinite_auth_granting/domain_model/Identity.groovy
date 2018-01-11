package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import groovy.transform.CompileStatic

@CompileStatic
class Identity {

    String identityName

    List<Authentication> authenticationList

    List<Authentication> getAuthenticationList() {
        return authenticationList
    }
}