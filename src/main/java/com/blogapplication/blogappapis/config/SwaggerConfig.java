package com.blogapplication.blogappapis.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private ApiKey apiKeys() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private List<SecurityContext> securityContexts() {
        return Arrays.asList(SecurityContext.builder().securityReferences(this.securityReferences()).build());
    }

    private List<SecurityReference> securityReferences() {
        AuthorizationScope[] scopes = new AuthorizationScope[] { new AuthorizationScope("global", "accessEverything") };
        return Arrays.asList(new SecurityReference("JWT", scopes));
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(this.getApiInfo())
                .securityContexts(this.securityContexts())
                .securitySchemes(Arrays.asList(this.apiKeys())).select()
                .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo("Blogging Application APIs",
                "This project is developed by Rahul Tiwari as a self-learning capstone project", "1.0.0",
                "", new Contact("Rahul Tiwari", "https://www.linkedin.com/in/rahultiwari15/", "rahultiwari.201307@gmail.com"), 
                "",
                "", Collections.emptyList());
    }

}
