package com.phoenix.shortLink.config;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("com.phoenix.shortLink.controller");
        register(MultiPartFeature.class);
        property(JspMvcFeature.TEMPLATE_BASE_PATH,"/WEB-INF");
        register(JspMvcFeature.class);
    }
}
