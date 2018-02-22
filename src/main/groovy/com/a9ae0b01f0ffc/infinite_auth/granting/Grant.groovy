package com.a9ae0b01f0ffc.infinite_auth.granting

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class Grant {


    @JsonProperty("service_name")
    String restResourceName

    String method

    @JsonProperty("uri_mask")
    String urlMask

    @JsonProperty("data_authorization_keys")
    String keyFieldSetJson

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Grant grant = (Grant) o

        if (keyFieldSetJson != grant.keyFieldSetJson) return false
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
        result = 31 * result + (keyFieldSetJson != null ? keyFieldSetJson.hashCode() : 0)
        return result
    }
}
