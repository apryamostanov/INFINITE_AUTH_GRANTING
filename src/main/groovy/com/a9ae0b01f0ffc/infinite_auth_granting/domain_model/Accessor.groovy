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

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Accessor accessor = (Accessor) o

        if (accessorName != accessor.accessorName) return false
        if (apiVersionName != accessor.apiVersionName) return false
        if (appName != accessor.appName) return false
        if (appVersion != accessor.appVersion) return false
        if (endpointName != accessor.endpointName) return false
        if (fiid != accessor.fiid) return false
        if (isForbidden != accessor.isForbidden) return false
        if (lookupPriority != accessor.lookupPriority) return false
        if (platform != accessor.platform) return false
        if (product != accessor.product) return false
        if (productGroup != accessor.productGroup) return false

        return true
    }

    int hashCode() {
        return (accessorName != null ? accessorName.hashCode() : 0)
    }
}