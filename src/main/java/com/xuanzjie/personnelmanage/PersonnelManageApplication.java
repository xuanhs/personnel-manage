package com.xuanzjie.personnelmanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(
        scanBasePackages = {"com.xuanzjie"}
)
@EnableEurekaClient
@EnableFeignClients(
        basePackages = {"com.xuanzjie"}
)
@EnableSwagger2
public class PersonnelManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonnelManageApplication.class, args);
    }

}
