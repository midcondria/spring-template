package com.commerce.team.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI serverApiConfig() {
        Server server = new Server();
        server.setUrl("/");
        server.description("백엔드 도메인");
        return new OpenAPI()
            .servers(List.of(server))
            .info(new Info().title("PAWLAND API")
                .description("PAWLAND API SWAGGER UI입니다."));
    }
}
