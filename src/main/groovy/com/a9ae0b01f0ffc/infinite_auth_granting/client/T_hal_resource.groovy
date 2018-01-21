package com.a9ae0b01f0ffc.infinite_auth_granting.client

import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const
import com.fasterxml.jackson.annotation.JsonIgnore

import static base.T_common_base_1_const.GC_FALSE
import static base.T_common_base_1_const.GC_TRUE
import static base.T_common_base_1_const.GC_ZERO
import static base.T_common_base_3_utils.not

class T_hal_resource {

    String resourceName = this.getClass().getSimpleName()
    String resourceSelfUrl

    @JsonIgnore
    String[] PC_INGORED_PROPERTY_NAMES = []

    Boolean match_with_conf(T_hal_resource i_conf_resource) {
        if (getClass() != i_conf_resource.class) return false
        T_hal_resource l_config_resource = getClass().cast(i_conf_resource) as T_hal_resource
        for (l_prop_name in properties.keySet()) {
            System.out.println("Property: " + l_prop_name)
            if (not(i_conf_resource.PC_INGORED_PROPERTY_NAMES.contains(l_prop_name))) {
                if (properties.get(l_prop_name) instanceof T_hal_resource) {
                    System.out.println((T_hal_resource) properties.get(l_prop_name))
                    System.out.println(i_conf_resource.properties.get(l_prop_name))
                    if (!((T_hal_resource) properties.get(l_prop_name)).match_with_conf(i_conf_resource.properties.get(l_prop_name) as T_hal_resource)) return GC_FALSE
                } else {
                    System.out.println(properties.get(l_prop_name))
                    System.out.println(i_conf_resource.properties.get(l_prop_name))
                    if (properties.get(l_prop_name) != i_conf_resource.properties.get(l_prop_name)) return GC_FALSE
                }
            } else {
                System.out.println("Ignoring property: " + l_prop_name)
            }
        }
        return GC_TRUE
    }

}
