package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_client_response
import com.a9ae0b01f0ffc.infinite_auth_granting.server.E_api_exception
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper

import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_6_util.*

@JsonIgnoreProperties(ignoreUnknown = true)
class Accessor {

    String resourceName = this.getClass().getSimpleName()
    String appName
    String accessorName
    String platform
    String appVersion
    String user
    String FIID
    String product
    String productGroup
    Endpoint endpoint
    Version apiVersion
    Accessor parentAccessor

}