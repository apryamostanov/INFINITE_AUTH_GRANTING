package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import groovy.transform.CompileStatic

@CompileStatic
class Token {

    String authorizationName
    String scopeName
    String identityName
    List<String> authenticationNamesList
    Map<String, List<String>> dataFieldListMap
    Date creationDate
    Date expiryDate
    Integer usageLimit
    List<ResourceGrant> resourceGrantList

}
