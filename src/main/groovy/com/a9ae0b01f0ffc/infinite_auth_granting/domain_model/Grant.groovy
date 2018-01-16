package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.client.ReferencingResource
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Grant  extends ReferencingResource{
    ReferencingResource restResource

    ReferencingResource methodSet

    ReferencingResource parentGrant

    ReferencingResource url

    ReferencingResource searchUrlSet

    ReferencingResource payloadKeyFieldSet

}
