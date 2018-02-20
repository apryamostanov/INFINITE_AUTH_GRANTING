package com.a9ae0b01f0ffc.infinite_auth_granting.config.data

import com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model.AccessorType
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_accessor_type_repository
import org.springframework.stereotype.Component
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.*

@Component
class G01_AccessorTypeGenerator {

    void generate_data(I_accessor_type_repository p_accessor_repository) {
        Set<AccessorType> l_entity_set = new HashSet<AccessorType>()

        l_entity_set.add(new AccessorType(accessorRole: GC_ACCESSOR_TYPE_ACCESS_CONTROL
                , lookupPriority: 0, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Access: Any accessor", appName: "%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new AccessorType(accessorRole: GC_ACCESSOR_TYPE_SCOPE_CONTROL
                , lookupPriority: 0, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Scope: Any accessor", appName: "%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new AccessorType(accessorRole: GC_ACCESSOR_TYPE_SCOPE_CONTROL
                , lookupPriority: 1, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Scope: Any White Labeled", appName: "%White%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new AccessorType(accessorRole: GC_ACCESSOR_TYPE_SCOPE_CONTROL
                , lookupPriority: 2, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Scope: Any White Labeled Single Currency", appName: "%White%Single%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new AccessorType(accessorRole: GC_ACCESSOR_TYPE_SCOPE_CONTROL
                , lookupPriority: 2, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Scope: Any White Labeled Multi Currency", appName: "%White%Multi%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new AccessorType(accessorRole: GC_ACCESSOR_TYPE_SCOPE_CONTROL
                , lookupPriority: 3, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Scope: Any LMN", appName: "%LMN%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new AccessorType(accessorRole: GC_ACCESSOR_TYPE_AUTHORIZATION_CONTROL
                , lookupPriority: 0, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Authorization: Any accessor", appName: "%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new AccessorType(accessorRole: GC_ACCESSOR_TYPE_AUTHORIZATION_CONTROL
                , lookupPriority: 0, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Authorization: Any White Labeled Multi Currency", appName: "%White%Multi%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new AccessorType(accessorRole: GC_ACCESSOR_TYPE_AUTHORIZATION_CONTROL
                , lookupPriority: 1, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Authorization: Any LMN", appName: "%LMN%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new AccessorType(accessorRole: GC_ACCESSOR_TYPE_AUTHORIZATION_CONTROL
                , lookupPriority: 2, osName: "%", platform: "%React%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Authorization: Any React", appName: "%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new AccessorType(accessorRole: GC_ACCESSOR_TYPE_ROUTING_CONTROL
                , lookupPriority: 0, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Routing: Any APAC QA Accessor", appName: "%QA%", apiVersionName: "%", grantingEndpointName: "%", validationEndpointName: "%", resourceEndpointGroupName: "QA"))

        l_entity_set.add(new AccessorType(accessorRole: GC_ACCESSOR_TYPE_ROUTING_CONTROL
                , lookupPriority: 0, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Routing: Any EMEA UAT Accessor", appName: "%EMEA UAT%", apiVersionName: "%", grantingEndpointName: "%", validationEndpointName: "%", resourceEndpointGroupName: "EMEA UAT"))

        l_entity_set.add(new AccessorType(accessorRole: GC_ACCESSOR_TYPE_ROUTING_CONTROL
                , lookupPriority: 0, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Routing: Any Sandbox Accessor", appName: "%Sandbox%", apiVersionName: "%", grantingEndpointName: "%", validationEndpointName: "%", resourceEndpointGroupName: "Sandbox"))

        p_accessor_repository.save(l_entity_set)
    }

}