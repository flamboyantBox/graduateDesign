package com.feng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Mr.Feng
 * @date 7/8/2022 10:26 AM
 */
@SpringBootApplication
@MapperScan(value = "com.feng.mapper")
@EnableSwagger2
@EnableScheduling
public class ServiceBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceBlogApplication.class, args);
    }
}
