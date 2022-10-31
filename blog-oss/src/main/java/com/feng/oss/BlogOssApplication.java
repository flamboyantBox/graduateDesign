package com.feng.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Mr.Feng
 * @date 7/14/2022 10:52 AM
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan({"com.feng"})
@EnableSwagger2
@EnableFeignClients
public class BlogOssApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogOssApplication.class, args);
    }
}
