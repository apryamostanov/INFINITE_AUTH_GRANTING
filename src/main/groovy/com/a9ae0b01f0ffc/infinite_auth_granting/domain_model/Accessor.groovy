package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_hal_resource
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Accessor extends T_hal_resource {


    String appName
    String accessorName
    String platform
    String appVersion
    String fiid
    String product
    String productGroup
    Integer lookupPriority
    String endpointName
    String apiVersionName
    Integer isForbidden

    @JsonIgnore
    String getLookupPriority() {
        return lookupPriority
    }

    @JsonIgnore
    Integer getIsForbidden() {
        return isForbidden
    }

}