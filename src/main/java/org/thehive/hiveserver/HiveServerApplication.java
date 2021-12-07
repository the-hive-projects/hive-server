package org.thehive.hiveserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.thehive.hiveserver.service.SessionService;
import org.thehive.hiveserver.session.LiveSessionManager;

@SpringBootApplication(proxyBeanMethods = false)
public class HiveServerApplication {

    // TODO: 10/31/2021 websocket session external service
    // TODO: 11/4/2021 json marshalling null handling
    // TODO: 11/4/2021 json unmarshalling unknown field handling
    // TODO: 11/6/2021 create image provider api and strategy when enable imag upload 
    // TODO: 11/6/2021 enable sessionId and add logout endpoint 

    public static void main(String[] args) {
        SpringApplication.run(HiveServerApplication.class, args);
    }

    @Bean
    @Profile("dev")
    public CommandLineRunner startDevDefaultSession(SessionService sessionService, LiveSessionManager liveSessionManager) {
        return args -> {
            var session = sessionService.findById("00000000000");
            liveSessionManager.startSession(session);
        };
    }

}
