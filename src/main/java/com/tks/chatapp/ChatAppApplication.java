package com.tks.chatapp;

import io.swagger.annotations.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Slf4j
@EnableAsync
@EnableJpaRepositories
@EnableJpaAuditing
public class ChatAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatAppApplication.class, args);
    }

}
