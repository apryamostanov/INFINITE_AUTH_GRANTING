package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import groovy.transform.CompileStatic

@CompileStatic
class ResourceGrant {

    String resourceName

    List<String> methodList

    /*Ownership by another resource with existing grant/access*/
    ResourceGrant parentResourceGrant

    String uri

    List<String> searchUriList

    Map<String, List<String>> payloadKeyFieldList

}
