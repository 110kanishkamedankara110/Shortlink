package com.phoenix.shortLink.config;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("com.phoenix.shortLink.controller");
        register(MultiPartFeature.class);
    }
}
