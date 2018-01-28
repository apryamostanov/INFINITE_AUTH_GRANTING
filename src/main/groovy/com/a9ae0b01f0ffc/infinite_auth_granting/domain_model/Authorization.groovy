package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_hal_resource
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_resource_set
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.stereotype.Component

import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.GC_STATUS_NEW

@JsonIgnoreProperties(ignoreUnknown = true)
@Component
class Authorization extends T_hal_resource {
    String authorizationName
    Accessor accessor


    T_resource_set<Authentication> authenticationSet

    Scope scope

    Integer durationSeconds

    Integer maxUsageCount

    T_resource_set<Authorization> prerequisiteAuthorizationSet
    Authorization refreshAuthorization
    @JsonFormat(timezone = "UTC")
    Date creationDate
    Date expiryDate
    String authorizationStatus = GC_STATUS_NEW
    String errorCode
    String jwt
    String authorizationType

    @JsonIgnore
    String[] p_ignored_property_names = ["resourceSelfUrl", "p_context", "creationDate", "expiryDate"]


    @JsonIgnore
    String getSortKeyValue() {
        return authorizationName
    }

}