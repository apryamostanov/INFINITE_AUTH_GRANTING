package com.a9ae0b01f0ffc.test_like_error

import com.a9ae0b01f0ffc.test_like_error.config.data.DataGenerator
import com.a9ae0b01f0ffc.test_like_error.config.domain_model.TestEntity
import com.a9ae0b01f0ffc.test_like_error.config.interfaces.ITestEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.hateoas.config.EnableHypermediaSupport

@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@org.springframework.boot.autoconfigure.SpringBootApplication
class SpringBootApplication implements CommandLineRunner {


    static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args)
    }

    @Autowired
    private DataGenerator p_data_generator

    @Autowired
    ITestEntityRepository testEntityRepository

    @Override
    void run(String... args) throws Exception {
        p_data_generator.generate_data()
        Set<TestEntity> testEntitySet = testEntityRepository.find_by_like_column("z")

        Set<TestEntity> testEntitySet2 = testEntityRepository.find_by_like_column("")
    }
}
