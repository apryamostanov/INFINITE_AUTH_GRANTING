package com.a9ae0b01f0ffc.infinite_auth

import com.a9ae0b01f0ffc.infinite_auth.config.data.T_data_generator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.hateoas.config.EnableHypermediaSupport

@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@SpringBootApplication
class T_spring_boot_application implements CommandLineRunner {


    static void main(String[] args) {
        SpringApplication.run(T_spring_boot_application.class, args)
    }

    @Autowired
    private T_data_generator p_data_generator

    @Override
    void run(String... args) throws Exception {
        p_data_generator.generate_data()
    }
}
