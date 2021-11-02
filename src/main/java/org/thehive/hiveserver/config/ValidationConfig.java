package org.thehive.hiveserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.thehive.hiveserver.validation.ValidationErrorMessageFormatter;

@Configuration
public class ValidationConfig {

    @Value("${validation.error.message.delimiter}")
    private String validationErrorMessageDelimiter;

    @Value("${validation.error.message.prefix}")
    private String validationErrorMessagePrefix;

    @Value("${validation.error.message.suffix}")
    private String validationErrorMessageSuffix;

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
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
        return new ValidationErrorMessageFormatter(validationErrorMessageDelimiter, validationErrorMessagePrefix, validationErrorMessageSuffix);
    }

}
