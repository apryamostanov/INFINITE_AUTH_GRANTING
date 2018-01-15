package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Grant {

    String resourceName = this.getClass().getSimpleName()
    RestResource restResource

    Set<Method> methodSet = new HashSet<Method>()

    Grant parentGrant

    Url url

    Set<Url> searchUrlSet = new HashSet<Url>()

    Set<DataField> payloadKeyFieldSet = new HashSet<DataField>()

}
