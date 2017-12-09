package com.a9ae0b01f0ffc.infinite_auth_granting

import com.a9ae0b01f0ffc.infinite_auth_configuration.domain_model.*
import com.a9ae0b01f0ffc.infinite_auth_configuration.interfaces.*
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class T_spring_boot_application implements CommandLineRunner {

    static void main(String[] args) {
        SpringApplication.run(T_spring_boot_application.class, args)
    }


    @Override
    void run(String... args) throws Exception {

    }
}
