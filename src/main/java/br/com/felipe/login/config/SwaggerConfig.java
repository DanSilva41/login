package br.com.felipe.login.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Configuration
@EnableSwagger2
@PropertySource("classpath:swagger/swagger.properties")
public class SwaggerConfig {

    @Value("${app.token-url:http://localhost:8092/api/thesolution/oauth/token}")
    private String accessTokenUri;

    @Value("${app.name}")
    private String applicationName;

    @Value("${api.title}")
    private String apiTitle;

    @Value("${api.description}")
    private String apiDescription;

    @Value("${app.version}")
    private String appVersion;

    @Value("${api.contact.name}")
    private String contactName;

    @Value("${api.contact.site}")
    private String contactSite;

    @Value("${api.contact.email}")
    private String contactEmail;

    @Bean
    public Docket api() {
        final ApiInfo applicationApiInfo = new ApiInfoBuilder()
            .title(apiTitle)
            .description(apiDescription)
            .version(appVersion)
            .contact(new Contact(contactName, contactSite, contactEmail))
            .build();

        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage("com.thesolution.projects.backend.auth.web.rest"))
            .paths(PathSelectors.any()).build()
            .apiInfo(applicationApiInfo)
            .securitySchemes(oAuth())
            .securityContexts(Collections.singletonList(securityContext()));
    }


    private List<SecurityScheme> oAuth() {
        List<AuthorizationScope> scopesJusticaDev = Arrays.asList(new AuthorizationScope("read", "Read all data"),
            new AuthorizationScope("write", "Write all data"));

        List<GrantType> gTypesInterno = new ArrayList<>();
        gTypesInterno.add(new ResourceOwnerPasswordCredentialsGrant(accessTokenUri));

        OAuth oAuthJusticaDev = new OAuth("Login Swagger", scopesJusticaDev, gTypesInterno);

        List<SecurityScheme> list = new ArrayList<>();
        list.add(oAuthJusticaDev);

        return list;
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[0];
        SecurityReference securityReferenceJusticaDev = new SecurityReference("Login Swagger", authorizationScopes);
        return Collections.singletonList(securityReferenceJusticaDev);
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder().clientId("swagger").clientSecret("sw@gg3r").build();
    }

}
