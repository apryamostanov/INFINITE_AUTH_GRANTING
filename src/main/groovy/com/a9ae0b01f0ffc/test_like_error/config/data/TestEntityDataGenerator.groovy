package com.a9ae0b01f0ffc.test_like_error.config.data

import com.a9ae0b01f0ffc.test_like_error.config.domain_model.TestEntity
import com.a9ae0b01f0ffc.test_like_error.config.interfaces.ITestEntityRepository
import org.springframework.stereotype.Component


@Component
class TestEntityDataGenerator {

    void generate_data(ITestEntityRepository p_accessor_repository) {
        Set<TestEntity> l_entity_set = new HashSet<TestEntity>()
        l_entity_set.add(new TestEntity(testLikeProperty: "%"))

        p_accessor_repository.save(l_entity_set)
        l_entity_set.clear()
        l_entity_set.add(new TestEntity(testLikeProperty: "%ABCD%"))

        p_accessor_repository.save(l_entity_set)
    }

}