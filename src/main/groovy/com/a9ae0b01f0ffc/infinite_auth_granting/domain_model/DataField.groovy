package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.CompileStatic
import io.swagger.annotations.ApiModelProperty

import javax.persistence.*

import static base.T_common_base_1_const.GC_EMPTY_STRING

@JsonIgnoreProperties(ignoreUnknown = true)
class DataField {

    String resourceName = this.getClass().getSimpleName()
    String fieldName = GC_EMPTY_STRING
    String fieldValue = GC_EMPTY_STRING

}
