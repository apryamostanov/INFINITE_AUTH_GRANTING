package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_hal_resource
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import static base.T_common_base_1_const.GC_EMPTY_STRING
import static base.T_common_base_1_const.GC_NULL_OBJ_REF

@JsonIgnoreProperties(ignoreUnknown = true)
class Version  extends T_hal_resource{
    String versionName = GC_EMPTY_STRING
    T_hal_resource parentVersion = GC_NULL_OBJ_REF as Version

}