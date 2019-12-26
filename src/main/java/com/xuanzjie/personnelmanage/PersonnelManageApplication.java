package com.xuanzjie.personnelmanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PersonnelManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonnelManageApplication.class, args);
    }

}
