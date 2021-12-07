package org.thehive.hiveserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Profile("dev")
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        // Don't use RequestHandlerSelectors.any() as apis method argument, because it includes all actuator's endpoints.
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("general-api")
                .apiInfo(new ApiInfoBuilder()
                        .description("Hive Server API general reference")
                        .contact(new Contact("GitHub", "https://github.com/the-hive-projects", null))
                        .title("General API")
                        .version("1.0")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.thehive.hiveserver.api"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(new BasicAuth("generalSecurity")));
    }

}
