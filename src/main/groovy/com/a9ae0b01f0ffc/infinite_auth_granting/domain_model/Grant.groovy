package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_hal_resource
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Grant  extends T_hal_resource{
    T_hal_resource restResource

    T_hal_resource methodSet

    T_hal_resource parentGrant

    T_hal_resource url

    T_hal_resource payloadKeyFieldSet

}
