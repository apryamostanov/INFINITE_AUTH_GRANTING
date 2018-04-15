package com.a9ae0b01f0ffc.infinite_auth

import com.a9ae0b01f0ffc.infinite_auth.base.T_auth_grant_base_4_const
import com.a9ae0b01f0ffc.infinite_auth.base.T_auth_grant_conf
import com.a9ae0b01f0ffc.infinite_auth.base.T_jersey_config
import com.a9ae0b01f0ffc.infinite_auth.config.data.T_data_generator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
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
