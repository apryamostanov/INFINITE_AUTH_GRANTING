package com.a9ae0b01f0ffc.infinite_auth_granting.client

import com.fasterxml.jackson.annotation.JsonIgnore

class T_hal_resource implements I_hal_resource {

    String resourceName = this.getClass().getSimpleName()
    String resourceSelfUrl

    @JsonIgnore
    String getResourceName() {
        return resourceName
    }


}
