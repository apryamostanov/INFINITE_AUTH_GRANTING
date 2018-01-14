package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import static base.T_common_base_1_const.GC_EMPTY_STRING

@JsonIgnoreProperties(ignoreUnknown = true)
class Url {

    String urlName = GC_EMPTY_STRING

}
