package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_hal_resource
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_resource_set
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Grant  extends T_hal_resource{
    RestResource restResource

    T_resource_set<Method> methodSet

    Url selfUrl

    T_resource_set<DataField> payloadKeyFieldSet

}
