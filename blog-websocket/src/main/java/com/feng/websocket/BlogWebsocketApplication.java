package com.feng.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Mr.Feng
 * @date 10/20/2022 6:44 PM
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableSwagger2
@EnableFeignClients
@ComponentScan({"com.feng.websocket"})
public class BlogWebsocketApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogWebsocketApplication.class, args);
    }
}
