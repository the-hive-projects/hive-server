package org.thehive.hiveserver.security;

import lombok.Data;

@Data
public class UrlEndpointWhitelist {

    private final String[] methodGetUrlEndpoints;
    private final String[] methodPostUrlEndpoints;

}
