package com.a9ae0b01f0ffc.infinite_auth_granting.client

import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.GC_IS_REFERENCED_NO

class ReferencingResource {

    String resourceName = this.getClass().getSimpleName()
    String resourceAbsoluteUrl
    String resourceAccessUrl
    Boolean isReferencing = GC_IS_REFERENCED_NO

}
