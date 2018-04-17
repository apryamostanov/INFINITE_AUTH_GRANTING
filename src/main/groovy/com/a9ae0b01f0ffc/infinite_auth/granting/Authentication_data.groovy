package com.a9ae0b01f0ffc.infinite_auth.granting

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class Authentication_data {

    @JsonProperty("public_data")
    HashMap<String, Object> publicDataFieldMap

    @JsonProperty("private_data")
    HashMap<String, Object> privateDataFieldMap

}
