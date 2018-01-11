package com.a9ae0b01f0ffc.infinite_auth_granting

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


    @Override
    void run(String... args) throws Exception {

    }
}
