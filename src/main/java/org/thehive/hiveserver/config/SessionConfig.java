package org.thehive.hiveserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thehive.hiveserver.session.*;

@Configuration
public class SessionConfig {

    @Bean
    @ConfigurationProperties("session")
    public SessionProperties sessionProperties() {
        return new SessionProperties();
    }

    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        var sessionProperties = sessionProperties();
        if (sessionProperties.getId().getGenerator().getType() == SessionProperties.Id.Generator.Types.NUMERICAL) {
            return new NumericalSessionIdGenerator(sessionProperties.getId().getLength());
        }
        throw new IllegalStateException("Session type is not supported, sessionType: " +
                sessionProperties.getId().getGenerator().getType());
    }

    @Bean
    public LiveSessionManager liveSessionManager() {
        return new LiveSessionManagerImpl();
    }

}
