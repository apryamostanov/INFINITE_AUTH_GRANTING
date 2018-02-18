package com.a9ae0b01f0ffc.test_like_error.config.data

import com.a9ae0b01f0ffc.test_like_error.config.interfaces.ITestEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DataGenerator {

    @Autowired
    private ITestEntityRepository p_accessor_repository

    @Autowired
    TestEntityDataGenerator p_AccessorGenerator

    void generate_data() {
        p_AccessorGenerator.generate_data(p_accessor_repository)
    }

}
