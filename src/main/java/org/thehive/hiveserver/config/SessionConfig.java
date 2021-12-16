package org.thehive.hiveserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.thehive.hiveserver.service.SessionService;
import org.thehive.hiveserver.session.NumericalSessionJoinIdGenerator;
import org.thehive.hiveserver.session.SessionJoinIdGenerator;
import org.thehive.hiveserver.session.SessionProperties;
import org.thehive.hiveserver.session.live.*;

@Slf4j
@EnableScheduling
@EnableAsync
@Configuration
public class SessionConfig {

    @Bean
    @ConfigurationProperties("session")
    public SessionProperties sessionProperties() {
        return new SessionProperties();
    }

    @Bean
    public SessionJoinIdGenerator sessionIdGenerator() {
        var sessionProperties = sessionProperties();
        if (sessionProperties.getId().getGenerator().getType() == SessionProperties.Id.Generator.Types.NUMERICAL) {
            return new NumericalSessionJoinIdGenerator(sessionProperties.getId().getLength());
        }
        throw new IllegalStateException("Session type is not supported, sessionType: " +
                sessionProperties.getId().getGenerator().getType());
    }

    @Bean
    public LiveSessionHolderStrategy liveSessionHolderStrategy() {
        return new InMemoryLiveSessionHolderStrategy();
    }

    @Bean
    public LiveSessionHolder liveSessionHolder() {
        return new DefaultLiveSessionHolder(liveSessionHolderStrategy(), sessionIdGenerator());
    }

    @Bean
    public LiveSessionExpirationHandler liveSessionExpirationHandler(SimpMessagingTemplate messagingTemplate) {
        return new ScheduledLiveSessionExpirationHandler(liveSessionHolder(), liveSessionMessagingService(messagingTemplate));
    }

    @Bean
    public LiveSessionMessagingService liveSessionMessagingService(SimpMessagingTemplate messagingTemplate) {
        return new LiveSessionMessagingServiceImpl(messagingTemplate);
    }

    @Bean
    @Profile("dev")
    public CommandLineRunner startDevDefaultSession(SessionService sessionService, LiveSessionHolder liveSessionHolder) {
        return args -> {
            var session = sessionService.findById(1);
            var liveSession = liveSessionHolder.add(session);
            log.info("Live session has been creates at startup, liveId: {}", liveSession.liveId);
        };
    }

}
