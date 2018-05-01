package com.a9ae0b01f0ffc.infinite_auth.granting

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Grant is the lowest level object in the Authorization structure.<p><p>
 * A Grant is provided only in the Response, as part of the Scope object.<p>
 * It identifies which specific REST Resource have been granted be accessed - and using which HTTP method (POST, GET, etc) and how much times within a single Scope (Authorization) it is allowed to be accessed<p>
 *
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
class Grant {

    /**
     *  Name of the REST resource being allowed.<p><p>
     *  This is typically a noun.<p>
     *  Used in Common Authentication Validation Workflow.<p>
     *  Should be exact match with URI Path. <p>
     *  Only checked when urlMask is not defined (see below). <p>
     *
     *
     * Example: "W2WTransferInitiateEnhanced" <p><p>
     *
     * */
    @JsonProperty("service_name")
    String restResourceName

    /**
     *  HTTP Method of the REST resource being allowed.<p>
     *  Used in Common Authentication Validation Workflow.
     *  One Grant allows strictly one Method - to make it possible to restrict different Methods for same REST Resource<p><p>
     *
     * Example: "GET" <p><p>
     *
     * */
    String method

    /**
     *  Regular expression matching the URI path.<p>
     *  Optional parameter; if defined it is used instead of "restResourceName" ("restResourceName" is ignored).<p>
     *  Used in Common Authentication Validation Workflow.<p>
     *  Supports Data Scope (keyFieldMap) substitutions - any map keys surrounded by "%" in "urlMask" are replaced with their values from Data Scope (if present)<p>
     *  E.g.: ".*?\/%accessor_id%\/.*?\/.*" will be replaced with ".*?\/Access: Any accessor\/.*?\/.*" before matching with URI Path<p><p>
     *
     * Example: ".*?\/%accessor_id%\/.*?\/.*" <p><p>
     *
     * */
    @JsonProperty("uri_mask")
    String urlMask

    /**
     *  Used in Common Authentication Validation Workflow.<p>
     *  Identifies name of the Validation Module - script (Rule) validating request payload (POST) or query parameters (GET) against Data Scope of the Authorization.<p><p>
     *  I.e. used to check that the functional request (e.g. GetCardDetails) is done against same Proxy Number OR Card Number
     *  - as it was authorized (and as defined in Authorization Data Scope - during Authorization Granting process).
     *
     * Example: "[CardNumber, ProxyNumber, PhoneNumber]" <p><p>
     *
     * */
    @JsonProperty("data_authorization_keys")
    String validationModuleName

    /**
     *  Defines how many functional requests Authorization Validations are allowed of this specific Grant (using Grant Type Id field) within a single Authorization<p>
     *  Used in Common Authentication Validation Workflow.
     *  Null means no limitation<p>
     *
     * Example: "1"
     *
     * */
    @JsonProperty("max_count")
    Integer maxUsageCountWithinAuthorization

    /**
     *  Reference to corresponding Grant Type entity<p>
     *  Used in Common Authentication Validation Workflow - to group the Authorization usages for specific grant -
     *  to check how many functional requests Authorization Validations have been done and if "Maximum Grant Count Within Authorization" has been exceeded.<p>
     *
     * Example: "5"
     *
     * */
    @JsonProperty("grant_type_id")
    Long grantTypeId

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Grant grant = (Grant) o

        if (validationModuleName != grant.validationModuleName) return false
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
        result = 31 * result + (validationModuleName != null ? validationModuleName.hashCode() : 0)
        return result
    }
}
