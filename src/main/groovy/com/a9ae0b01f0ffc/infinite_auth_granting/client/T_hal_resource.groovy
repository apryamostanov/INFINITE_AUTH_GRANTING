package com.a9ae0b01f0ffc.infinite_auth_granting.client

import com.fasterxml.jackson.annotation.JsonIgnore

import static base.T_common_base_1_const.GC_FALSE
import static base.T_common_base_1_const.GC_TRUE
import static base.T_common_base_3_utils.not

class T_hal_resource {

    String resourceName = this.getClass().getSimpleName()
    String resourceSelfUrl

    @JsonIgnore
    String getResourceSelfUrl() {
        return resourceSelfUrl
    }

    @JsonIgnore
    String[] p_ignored_property_names = ["resourceSelfUrl"]



    @JsonIgnore
    String getSortKeyValue() {
        return resourceSelfUrl
    }

}
