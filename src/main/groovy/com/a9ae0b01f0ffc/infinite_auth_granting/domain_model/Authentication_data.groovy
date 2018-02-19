package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class Authentication_data {

    @JsonProperty("public_data")
    HashMap<String, Object> publicDataFieldSet

    @JsonProperty("private_data")
    HashMap<String, Object> privateDataFieldSet

}
