package org.thehive.hiveserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"org.thehive.hiveserver.repository"})
public class JpaConfig {

}
