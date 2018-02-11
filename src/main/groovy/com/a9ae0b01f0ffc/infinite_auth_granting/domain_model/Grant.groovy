package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Grant {
    String restResourceName

    String method

    String urlMask

    Set<String> keyFieldSet

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Grant grant = (Grant) o

        if (keyFieldSet != grant.keyFieldSet) return false
        if (method != grant.method) return false
        if (restResourceName != grant.restResourceName) return false
        if (urlMask != grant.urlMask) return false

        return true
    }

    int hashCode() {
        int result
        result = restResourceName.hashCode()
        result = 31 * result + method.hashCode()
        result = 31 * result + (urlMask != null ? urlMask.hashCode() : 0)
        result = 31 * result + (keyFieldSet != null ? keyFieldSet.hashCode() : 0)
        return result
    }
}
