package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.client.ReferencingResource
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Accessor extends ReferencingResource{


    String appName
    String accessorName
    String platform
    String appVersion
    String user
    String FIID
    String product
    String productGroup
    ReferencingResource endpoint
    ReferencingResource apiVersion
    ReferencingResource parentAccessor

}