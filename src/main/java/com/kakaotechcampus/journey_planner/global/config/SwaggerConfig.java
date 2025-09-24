package com.kakaotechcampus.journey_planner.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .servers(List.of(
                        new Server().url("http://18.118.161.98:8080").description("Production HTTPS 서버"),
                        new Server().url("http://localhost:8080").description("로컬 개발 서버")
                ));
    }

    private Info apiInfo() {
        return new Info()
                .title("Journey Planner API")
                .description("카카오테크캠퍼스 3기 경북대 4팀의 백엔드 API 명세")
                .version("1.0.0");
    }
}