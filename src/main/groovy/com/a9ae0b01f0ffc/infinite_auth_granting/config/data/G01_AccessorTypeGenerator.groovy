package com.a9ae0b01f0ffc.infinite_auth_granting.config.data

import com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model.AccessorType
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_accessor_type_repository
import org.springframework.stereotype.Component

@Component
class G01_AccessorTypeGenerator {

    void generate_data(I_accessor_type_repository p_accessor_repository) {
        Set<AccessorType> l_entity_set = new HashSet<AccessorType>()
        l_entity_set.add(new AccessorType(lookupPriority: -1, platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Any accessor", appName: "%", endpointName: "%", apiVersionName: "%"))
        l_entity_set.add(new AccessorType(lookupPriority: 0, platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "Single Currency", accessorName: "Any accessor Single Currency 0.0.x", appName: "%", endpointName: "%", apiVersionName: "0.0.%"))
        l_entity_set.add(new AccessorType(lookupPriority: 0, platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "Multi Currency", accessorName: "Any accessor Multi Currency 0.0.x", appName: "%", endpointName: "%", apiVersionName: "0.0.%"))
        l_entity_set.add(new AccessorType(lookupPriority: 0, platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "Single Currency", accessorName: "Any accessor Single Currency 1.0.x", appName: "%", endpointName: "%", apiVersionName: "1.0.%"))
        l_entity_set.add(new AccessorType(lookupPriority: 0, platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "Multi Currency", accessorName: "Any accessor Multi Currency 1.0.x", appName: "%", endpointName: "%", apiVersionName: "1.0.%"))
        l_entity_set.add(new AccessorType(lookupPriority: 0, platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "Single Currency", accessorName: "Any accessor Single Currency 2.0.x", appName: "%", endpointName: "%", apiVersionName: "2.0.%"))
        l_entity_set.add(new AccessorType(lookupPriority: 0, platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "Multi Currency", accessorName: "Any accessor Multi Currency 2.0.x", appName: "%", endpointName: "%", apiVersionName: "2.0.%"))
        l_entity_set.add(new AccessorType(lookupPriority: 1, platform: "%", appVersion: "%", fiid: "1100", product: "%", productGroup: "Multi Currency", accessorName: "Whitelabeled Multi Currency (Staging)", appName: "Whitelabeled Multi Currency (Staging)", endpointName: "Staging", apiVersionName: "1.0.%"))
        l_entity_set.add(new AccessorType(lookupPriority: 1, platform: "%", appVersion: "%", fiid: "1100", product: "%", productGroup: "Single Currency", accessorName: "GFS Single Currency (Staging)", appName: "GFS Single Currency (Staging)", endpointName: "Staging", apiVersionName: "1.0.%"))
        l_entity_set.add(new AccessorType(lookupPriority: 1, platform: "%", appVersion: "%", fiid: "1100", product: "%", productGroup: "Multi Currency", accessorName: "LMN Multi Currency Native (FT2 Development)", appName: "LMN Multi Currency Native (FT2 Development)", endpointName: "FT2 Development", apiVersionName: "2.0.%"))
        l_entity_set.add(new AccessorType(lookupPriority: 1, platform: "React", appVersion: "%", fiid: "1100", product: "%", productGroup: "Multi Currency", accessorName: "LMN Multi Currency React (FT2 Development)", appName: "LMN Multi Currency React (FT2 Development)", endpointName: "FT2 Development", apiVersionName: "2.0.%"))
        l_entity_set.add(new AccessorType(lookupPriority: 1, platform: "%", appVersion: "%", fiid: "1100", product: "%", productGroup: "Single Currency", accessorName: "", appName: "Whitelabeled Single Currency (Sandbox)", endpointName: "Sandbox", apiVersionName: "0.0.%"))
        l_entity_set.add(new AccessorType(lookupPriority: 1, platform: "%", appVersion: "%", fiid: "1100", product: "%", productGroup: "Multi Currency", accessorName: "Whitelabeled Multi Currency (Sandbox)", appName: "Whitelabeled Multi Currency (Sandbox)", endpointName: "Sandbox", apiVersionName: "0.0.%"))
        p_accessor_repository.save(l_entity_set)
    }

}