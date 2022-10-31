package com.feng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Mr.Feng
 * @date 10/20/2022 1:51 PM
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients
@EnableSwagger2
public class BlogPaymentApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogPaymentApplication.class, args);
    }
}
