package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.CompileStatic

@CompileStatic
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

}