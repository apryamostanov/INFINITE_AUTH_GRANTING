package com.a9ae0b01f0ffc.test_like_error.config.data

import com.a9ae0b01f0ffc.test_like_error.config.domain_model.TestEntity
import com.a9ae0b01f0ffc.test_like_error.config.interfaces.ITestEntityRepository
import org.springframework.stereotype.Component


@Component
class TestEntityDataGenerator {

    void generate_data(ITestEntityRepository p_accessor_repository) {
        Set<TestEntity> l_entity_set = new HashSet<TestEntity>()
        l_entity_set.add(new TestEntity(lookupPriority: -1, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Any accessor", appName: "%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new TestEntity(lookupPriority: 0, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Any White Labeled", appName: "%White%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new TestEntity(lookupPriority: 1, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "Single Currency", accessorName: "Any White Labeled Single Currency", appName: "%White%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new TestEntity(lookupPriority: 1, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "Multi Currency", accessorName: "Any White Labeled Multi Currency", appName: "%White%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new TestEntity(lookupPriority: 2, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "Multi Currency", accessorName: "Any ABCD", appName: "%ABCD%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new TestEntity(lookupPriority: 3, osName: "%", platform: "%React%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Any React", appName: "%", grantingEndpointName: "%", apiVersionName: "%"))

        l_entity_set.add(new TestEntity(lookupPriority: 0, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Any APAC QA Accessor", appName: "%QA%", apiVersionName: "%", grantingEndpointName: "%", validationEndpointName: "%", resourceEndpointGroupName: "QA"))

        l_entity_set.add(new TestEntity(lookupPriority: 0, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Any EMEA UAT Accessor", appName: "%EMEA UAT%", apiVersionName: "%", grantingEndpointName: "%", validationEndpointName: "%", resourceEndpointGroupName: "EMEA UAT"))

        l_entity_set.add(new TestEntity(lookupPriority: 0, osName: "%", platform: "%", appVersion: "%", fiid: "%", product: "%", productGroup: "%", accessorName: "Any Sandbox Accessor", appName: "%Sandbox%", apiVersionName: "%", grantingEndpointName: "%", validationEndpointName: "%", resourceEndpointGroupName: "Sandbox"))

        p_accessor_repository.save(l_entity_set)
        p_accessor_repository.save(l_entity_set)
    }

}