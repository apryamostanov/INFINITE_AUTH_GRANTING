package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.client.ReferencingResource
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import static base.T_common_base_1_const.GC_EMPTY_STRING

@JsonIgnoreProperties(ignoreUnknown = true)
class DataField  extends ReferencingResource{
    String fieldName = GC_EMPTY_STRING
    String fieldValue = GC_EMPTY_STRING

}
