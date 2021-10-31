package org.thehive.hiveserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(proxyBeanMethods = false)
public class HiveServerApplication {

    // TODO: 10/31/2021 user entity validation
    // TODO: 10/31/2021 session external service
    // TODO: 10/31/2021 custom persistence error or return 404 instead of internal server error

    public static void main(String[] args) {
        SpringApplication.run(HiveServerApplication.class, args);
    }

}
