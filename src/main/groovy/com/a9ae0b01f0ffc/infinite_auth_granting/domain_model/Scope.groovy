package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_hal_resource
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_resource_set
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Scope extends T_hal_resource {
    String scopeName
    Accessor accessor
    Set<Grant> grantSet

    HashMap<String, String> keyFieldMap

    @JsonIgnore
    Accessor getAccessor() {
        return accessor
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Scope scope = (Scope) o

        if (accessor != scope.accessor) return false
        if (grantSet != scope.grantSet) return false
        if (keyFieldMap != scope.keyFieldMap) return false
        if (scopeName != scope.scopeName) return false

        return true
    }

    int hashCode() {
        int result
        result = scopeName.hashCode()
        result = 31 * result + (accessor != null ? accessor.hashCode() : 0)
        result = 31 * result + (grantSet != null ? grantSet.hashCode() : 0)
        result = 31 * result + (keyFieldMap != null ? keyFieldMap.hashCode() : 0)
        return result
    }
}