package com.a9ae0b01f0ffc.infinite_auth_granting.client

import com.fasterxml.jackson.annotation.JsonIgnore

interface I_hal_resource {

    @JsonIgnore
    String getResourceSelfUrl()

    void setResourceSelfUrl(String i_resource_self_url)

}