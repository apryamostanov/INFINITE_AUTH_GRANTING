package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_hal_resource
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Accessor extends T_hal_resource{


    String appName
    String accessorName
    String platform
    String appVersion
    String user
    String FIID
    String product
    String productGroup
    T_hal_resource endpoint
    T_hal_resource apiVersion
    T_hal_resource parentAccessor

}