package com.a9ae0b01f0ffc.infinite_auth.granting

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Scope is a group of Grants<p>
 * Scope corresponds either to a specific screen/activity/functionality on the Client application - or a logical group of services<p>
 * Defines which access is requested by user (Request) as well as which Grants (APIs) are allowed within a Authorization (in Response)<p><p>
 *
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
class Scope {

    /**
     *  Scope name.<p>
     *  Request and Response field<p>
     *      Specifies a predefined group of access grants (API services) that is requested by user (Request)
     *      - and the resulting specifc list of grants (in case of successful Response)
     *
     * Example: "Anonymous Services" <p><p>
     *
     * */
    String scopeName

    /**
     * Response field<p>
     *  List of Grants accessible using this Authorization.<p>
     *  Note: ALL of the Grant Types are allowed.<p>
     *
     * */
    @JsonProperty("service_scope")
    Set<Grant> grantSet


    /**
     * Response field. Filled by Authorization Granting process - Common Authentication Workflow - using Authentication Modules.<p>
     * Specifies the key identifiers allowed to be used in conjunction with the allowed Service Scope (list of allowed services)<p>
     * Values get appended by Authentication Modules as well as being copied from Prerequisite Authorization.<p>
     * In case of conflict of appended field names (e.g. when a field with same name exists but has a different value) - Authorization Grating process fails.<p><p>
     *     Example: ["proxy_number": 34252345, "account_number": 3485438748573234]<p>
     * This structure is passed to Validation Module of specific Grant during the Authorization Validation process - to validate against
     * the Functional API request data (Query parameters or Payload fields).<p>
     *     This is a superset of all underlying prerequisite authorizations<p><p>
     *
     */
    @JsonProperty("data_scope")
    HashMap<String, String> keyFieldMap


    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Scope scope = (Scope) o

        //if (accessor != scope.accessor) return false
        if (grantSet != scope.grantSet) return false
        if (keyFieldMap != scope.keyFieldMap) return false
        if (scopeName != scope.scopeName) return false

        return true
    }

    int hashCode() {
        int result
        result = scopeName.hashCode()
        //result = 31 * result + (accessor != null ? accessor.hashCode() : 0)
        result = 31 * result + (grantSet != null ? grantSet.hashCode() : 0)
        result = 31 * result + (keyFieldMap != null ? keyFieldMap.hashCode() : 0)
        return result
    }
}