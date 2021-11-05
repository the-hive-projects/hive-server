package org.thehive.hiveserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.thehive.hiveserver.service.UserService;
import org.thehive.hiveserver.validation.UserUniquenessValidationFilter;
import org.thehive.hiveserver.validation.UserUniquenessValidationFilterImpl;
import org.thehive.hiveserver.validation.ValidationErrorMessageFormatter;
import org.thehive.hiveserver.validation.ValidationProperties;

@Configuration
public class ValidationConfig {

    @ConfigurationProperties(prefix = "validation")
    @Bean
    public ValidationProperties validationProperties() {
        return new ValidationProperties();
    }

    @Bean
    public UserUniquenessValidationFilter userUniquenessValidationFilter(UserService userService) {
        return new UserUniquenessValidationFilterImpl(userService,
                validationProperties().getMessage().getUniqueness().getUsername(),
                validationProperties().getMessage().getUniqueness().getEmail());
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(validationProperties().getMessage().getSource());
        messageSource.setDefaultEncoding(validationProperties().getMessage().getEncoding());
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public ValidationErrorMessageFormatter validationErrorMessageFormatter() {
        return new ValidationErrorMessageFormatter(validationProperties().getMessage().getFormat());
    }

}
