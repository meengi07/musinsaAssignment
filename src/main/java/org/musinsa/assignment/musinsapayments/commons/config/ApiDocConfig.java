package org.musinsa.assignment.musinsapayments.commons.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocConfig {

    @Bean
    public OpenAPI setup() {
        return new OpenAPI()
            .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
            .title("Musinsa Payments API")
            .description("This is a assignment for Musinsa Payments")
            .version("1.0.0");
    }
}
