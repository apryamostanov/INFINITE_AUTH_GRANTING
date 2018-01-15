package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_resource_set
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.GC_IS_CACHED_NO

@JsonIgnoreProperties(ignoreUnknown = true)
class Identity {

    String resourceName = this.getClass().getSimpleName()
    String resourceUrl
    String cacheUrl
    Boolean isCached = GC_IS_CACHED_NO
    String identityName

    T_resource_set<Authentication> authenticationSet

}