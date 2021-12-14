package org.thehive.hiveserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.thehive.hiveserver.service.SessionService;
import org.thehive.hiveserver.session.LiveSessionHolder;

@SpringBootApplication(proxyBeanMethods = false)
public class HiveServerApplication {

    // TODO: 10/31/2021 websocket session external service
    // TODO: 11/4/2021 json marshalling null handling
    // TODO: 11/4/2021 json unmarshalling unknown field handling
    // TODO: 11/6/2021 create image provider api and strategy when enable imag upload 
    // TODO: 11/6/2021 enable sessionId and add logout endpoint 
    // TODO: 12/8/2021 reviews error messages
    // TODO: 12/8/2021 email pattern check
    // TODO: 12/8/2021 remove dev profiled beans and session controls
    // TODO: 12/8/2021 check for not joined but started sessions
    // TODO: 12/8/2021 control subscriptions when directly disconnect in client or desktop
    // TODO: 12/14/2021 adds cache
    // TODO: 12/14/2021 adds session management
    // TODO: 12/14/2021 manage hiberante eager fetch type

    public static void main(String[] args) {
        SpringApplication.run(HiveServerApplication.class, args);
    }

    @Bean
    @Profile("dev")
    public CommandLineRunner startDevDefaultSession(SessionService sessionService, LiveSessionHolder liveSessionHolder) {
        return args -> {
            var session = sessionService.findById(1);
            liveSessionHolder.addSession(session);
        };
    }

}
