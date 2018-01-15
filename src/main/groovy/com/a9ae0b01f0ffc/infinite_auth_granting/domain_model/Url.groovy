package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import static base.T_common_base_1_const.GC_EMPTY_STRING
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.GC_IS_CACHED_NO

@JsonIgnoreProperties(ignoreUnknown = true)
class Url {

    String resourceName = this.getClass().getSimpleName()
    String resourceUrl
    String cacheUrl
    Boolean isCached = GC_IS_CACHED_NO
    String urlName = GC_EMPTY_STRING

}
