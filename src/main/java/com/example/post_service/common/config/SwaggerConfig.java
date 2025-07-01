package com.example.post_service.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private static final String BEARER_TOKEN_PREFIX = "Bearer";

    @Bean
    public OpenAPI openAPI() {
        String securityJwtName = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);
        
        Components components = new Components()
                .addSecuritySchemes(securityJwtName, new SecurityScheme()
                        .name(securityJwtName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme(BEARER_TOKEN_PREFIX)
                        .bearerFormat(securityJwtName)
                        .description("JWT 토큰을 입력하세요. Gateway에서 인증된 토큰이 필요합니다."));

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components)
                .servers(List.of(
                        new Server().url("/post-service").description("Production Server"),
                        new Server().url("http://localhost:8080").description("Local Development Server")
                ))
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Post Service API Documentation")
                .description("""
                    ## Post Service API
                    
                    게시글과 카테고리 관리를 위한 마이크로서비스 API입니다.
                    
                    ### 주요 기능
                    - **게시글 관리**: 게시글 생성, 수정, 조회, 존재 여부 확인
                    - **카테고리 관리**: 메인/서브 카테고리 및 카테고리 리스트 관리
                    
                    ### 인증
                    - Gateway를 통한 JWT 토큰 인증
                    - `X-Member-UUID` 헤더를 통한 사용자 식별
                    
                    ### 데이터베이스
                    - **Post**: MongoDB (문서 기반)
                    - **Category**: MySQL (관계형)
                    
                    ### 이벤트 스트리밍
                    - Kafka를 통한 게시글 생성 이벤트 발행
                    
                    ### 에러 코드
                    - 2000-2099: Post 관련 에러
                    - 2100-2199: Category 관련 에러
                    """)
                .version("1.0.0")
                .contact(new Contact()
                        .name("Post Service Team")
                        .email("post-service@example.com")
                        .url("https://github.com/example/post-service"))
                .license(new License()
                        .name("MIT License")
                        .url("https://opensource.org/licenses/MIT"));
    }
}