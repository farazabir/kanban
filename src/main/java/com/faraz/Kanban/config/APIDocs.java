package com.faraz.Kanban.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIDocs {
    @Bean
    public OpenAPI simplestOpenAPI() {
        return new OpenAPI().info(
                new Info().title("Kanban API").version("v1")
        );
    }
}
