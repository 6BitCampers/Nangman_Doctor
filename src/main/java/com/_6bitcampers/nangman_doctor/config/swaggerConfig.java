package com._6bitcampers.nangman_doctor.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class swaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String springDocVersion) {
        return new OpenAPI()
                .info(new Info()
                        .title("낭만닥터 Rest API DOCS")
                        .description("낭만닥터 Rest API DOCS입니다.")
                        .version(springDocVersion)
                        .contact(new Contact().name("Nangman Doctor").email("8282qwe@gmail.com").url("http://deploysemi.midichi.kro.kr")));
    }
}

