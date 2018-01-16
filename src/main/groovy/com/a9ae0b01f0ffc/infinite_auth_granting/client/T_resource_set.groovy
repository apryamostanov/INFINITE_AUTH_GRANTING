package com.a9ae0b01f0ffc.infinite_auth_granting.client

class T_resource_set<T> extends ReferencingResource {

    String resourceName = "ResourceSet"

    Set<T> resourceSet = new HashSet<T>()

}
