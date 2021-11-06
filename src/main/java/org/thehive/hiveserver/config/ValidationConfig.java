package org.thehive.hiveserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.thehive.hiveserver.entity.User;
import org.thehive.hiveserver.service.UserService;
import org.thehive.hiveserver.validation.ValidationErrorMessageFormatter;
import org.thehive.hiveserver.validation.ValidationProperties;
import org.thehive.hiveserver.validation.filter.UserUniquenessValidationFilterUnit;
import org.thehive.hiveserver.validation.filter.ValidationFilterChain;
import org.thehive.hiveserver.validation.filter.ValidationFilterChainImpl;

@Configuration
public class ValidationConfig {

    @ConfigurationProperties(prefix = "validation")
    @Bean
    public ValidationProperties validationProperties() {
        return new ValidationProperties();
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

    @Bean
    public ValidationFilterChain<User> userValidationFilterChain() {
        return new ValidationFilterChainImpl<>();
    }

    @Bean
    public UserUniquenessValidationFilterUnit userUniquenessValidationFilterUnit(UserService userService) {
        return new UserUniquenessValidationFilterUnit(userService,
                validationProperties().getMessage().getUniqueness().getUsername(),
                validationProperties().getMessage().getUniqueness().getEmail());
    }

    @Autowired
    public void addUserValidationFilterUnitsToChain(ValidationFilterChain<User> validationFilterChain,
                                                    UserUniquenessValidationFilterUnit userUniquenessValidationFilterUnit) {
        validationFilterChain.addFilter(userUniquenessValidationFilterUnit);
    }

}
