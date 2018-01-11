package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.CompileStatic

import static base.T_common_base_1_const.GC_EMPTY_STRING
import static base.T_common_base_1_const.GC_NULL_OBJ_REF

@CompileStatic
@JsonIgnoreProperties(ignoreUnknown = true)
class Endpoint {

    String endpointName = GC_EMPTY_STRING
    Version apiVersion = GC_NULL_OBJ_REF as Version

}