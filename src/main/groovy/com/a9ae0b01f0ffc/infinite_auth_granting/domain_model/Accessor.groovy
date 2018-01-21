package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_hal_resource
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import static base.T_common_base_3_utils.not
import static base.T_common_base_3_utils.not
import static base.T_common_base_3_utils.not
import static base.T_common_base_3_utils.not
import static base.T_common_base_3_utils.not
import static base.T_common_base_3_utils.not
import static base.T_common_base_3_utils.not
import static base.T_common_base_3_utils.not

@JsonIgnoreProperties(ignoreUnknown = true)
class Accessor extends T_hal_resource{


    String appName
    String accessorName
    String platform
    String appVersion
    String user
    String fiid
    String product
    String productGroup
    Integer lookupPriority
    Endpoint endpoint
    Version apiVersion

}