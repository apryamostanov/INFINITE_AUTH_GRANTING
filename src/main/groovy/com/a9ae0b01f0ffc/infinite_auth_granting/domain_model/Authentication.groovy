package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_hal_resource
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_resource_set
import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
class Authentication  extends T_hal_resource{
    String authenticationName

    T_resource_set<DataField> publicDataFieldSet

    T_resource_set<DataField> privateDataFieldSet

    T_resource_set<DataField> keyFieldSet

    T_resource_set<DataField> functionalFieldSet

    String authenticationStatus = T_auth_grant_base_4_const.GC_STATUS_NEW


}