package com.a9ae0b01f0ffc.infinite_auth_granting.client

import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_5_context

import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.*

class T_resource_set<T> extends T_hal_resource {

    String resourceName = "ResourceSet"

    Set<T> resourceSet = new HashSet<T>()

    @Override
    Boolean match_with_conf(T_hal_resource i_conf_resource, T_auth_grant_base_5_context i_context) {
        if (getClass() != i_conf_resource.class) return false
        T_resource_set<T_hal_resource> l_conf_resource = (T_resource_set) i_conf_resource
        if (l_conf_resource.resourceSet.size() != resourceSet.size()) return false
        if (l_conf_resource.resourceSet.size() == resourceSet.size() && resourceSet.size() == GC_ZERO) return true
        resourceSet = resourceSet.sort { it.getSortKeyValue() }
        l_conf_resource.resourceSet = l_conf_resource.resourceSet.sort { it.getSortKeyValue() }
        for (Integer l_resource_index = GC_ZERO; l_resource_index < l_conf_resource.resourceSet.size(); l_resource_index ++) {
            if (l_resource_index >= resourceSet.size()) return false
            if (not((resourceSet[l_resource_index] as T_hal_resource).match_with_conf(l_conf_resource.resourceSet[l_resource_index], i_context))) {
                return false
            }
        }
        return true
    }
}
