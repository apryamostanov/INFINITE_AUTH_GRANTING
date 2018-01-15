package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.CompileStatic
import io.swagger.annotations.ApiModelProperty

import javax.persistence.*

import static base.T_common_base_1_const.GC_EMPTY_STRING
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.GC_IS_CACHED_NO

@JsonIgnoreProperties(ignoreUnknown = true)
class DataField {

    String resourceName = this.getClass().getSimpleName()
    String resourceUrl
    String cacheUrl
    Boolean isCached = GC_IS_CACHED_NO
    String fieldName = GC_EMPTY_STRING
    String fieldValue = GC_EMPTY_STRING

}
