package com.a9ae0b01f0ffc.infinite_auth_granting.config.data

import com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model.AccessorType
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_accessor_type_repository
import org.springframework.stereotype.Component
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.*

@Component
class G01_AccessorTypeGenerator {

    void generate_data(I_accessor_type_repository p_accessor_repository) {
        Set<AccessorType> l_entity_set = new HashSet<AccessorType>()
        l_entity_set.add(new AccessorType(roleSet: [GC_ACCESSOR_TYPE_AUTHORIZATION_CONTROL, GC_ACCESSOR_TYPE_SCOPE_CONTROL, GC_ACCESSOR_TYPE_ACCESS_CONTROL]
                , lookupPriority: -1, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Any accessor", appName: "%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new AccessorType(roleSet: [GC_ACCESSOR_TYPE_SCOPE_CONTROL]
                , lookupPriority: 0, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Any White Labeled", appName: "%White%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new AccessorType(roleSet: [GC_ACCESSOR_TYPE_SCOPE_CONTROL]
                , lookupPriority: 1, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "Single Currency", accessorName: "Any White Labeled Single Currency", appName: "%White%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new AccessorType(roleSet: [GC_ACCESSOR_TYPE_AUTHORIZATION_CONTROL, GC_ACCESSOR_TYPE_SCOPE_CONTROL]
                , lookupPriority: 1, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "Multi Currency", accessorName: "Any White Labeled Multi Currency", appName: "%White%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new AccessorType(roleSet: [GC_ACCESSOR_TYPE_AUTHORIZATION_CONTROL, GC_ACCESSOR_TYPE_SCOPE_CONTROL]
                , lookupPriority: 2, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "Multi Currency", accessorName: "Any LMN", appName: "%LMN%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new AccessorType(roleSet: [GC_ACCESSOR_TYPE_AUTHORIZATION_CONTROL]
                , lookupPriority: 3, osName: "%", platform: "%React%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Any React", appName: "%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new AccessorType(roleSet: [GC_ACCESSOR_TYPE_ROUTING_CONTROL]
                , lookupPriority: 0, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Any APAC QA Accessor", appName: "%QA%", apiVersionName: "%", grantingEndpointName: "%", validationEndpointName: "%", resourceEndpointGroupName: "QA"))

        l_entity_set.add(new AccessorType(roleSet: [GC_ACCESSOR_TYPE_ROUTING_CONTROL]
                , lookupPriority: 0, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Any EMEA UAT Accessor", appName: "%EMEA UAT%", apiVersionName: "%", grantingEndpointName: "%", validationEndpointName: "%", resourceEndpointGroupName: "EMEA UAT"))

        l_entity_set.add(new AccessorType(roleSet: [GC_ACCESSOR_TYPE_ROUTING_CONTROL]
                , lookupPriority: 0, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Any Sandbox Accessor", appName: "%Sandbox%", apiVersionName: "%", grantingEndpointName: "%", validationEndpointName: "%", resourceEndpointGroupName: "Sandbox"))

        p_accessor_repository.save(l_entity_set)
    }

}