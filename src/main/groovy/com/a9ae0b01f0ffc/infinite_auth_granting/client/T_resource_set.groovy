package com.a9ae0b01f0ffc.infinite_auth_granting.client

class T_resource_set<T> {

    String resourceName = "ResourceSet"
    String resourceUrl
    String cacheUrl
    Boolean isCached
    Set<T> resourceSet = new HashSet<T>()

}
