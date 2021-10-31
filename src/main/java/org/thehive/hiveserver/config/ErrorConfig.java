package org.thehive.hiveserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thehive.hiveserver.error.ExceptionErrorAttributes;

@Configuration
public class ErrorConfig {

    @Bean
    @ConfigurationProperties(prefix = "server.error")
    public ErrorProperties errorProperties(){
        return new ErrorProperties();
    }

    @Bean
    public ErrorAttributes errorAttributes(@Value("${server.error.include-method:true}") boolean includeMethod){
        return new ExceptionErrorAttributes(includeMethod);
    }

}
