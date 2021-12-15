package org.thehive.hiveserver.actuator;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import org.thehive.hiveserver.session.live.LiveSessionHolderStrategy;

@Component
@RequiredArgsConstructor
@Endpoint(id = "liveSession")
public class LiveSessionHealthEndpoint {

    private final LiveSessionHolderStrategy strategy;

    @ReadOperation
    public LiveSessionHealth health() {
        var sessionHealth = new LiveSessionHealth();
        sessionHealth.holderStrategyClass = strategy.getClass();
        sessionHealth.count = strategy.count();
        return sessionHealth;
    }

    @Getter
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private static class LiveSessionHealth {

        private Class<? extends LiveSessionHolderStrategy> holderStrategyClass;
        private int count;

    }

}
