package com.a9ae0b01f0ffc.infinite_auth_granting.client

import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.*

class T_resource_set<T> extends T_hal_resource {

    String resourceName = "ResourceSet"

    Set<T> resourceSet = new HashSet<T>()


}
