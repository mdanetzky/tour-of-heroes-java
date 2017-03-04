package com.tour.of.heroes.service;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Created by matthias on 02/03/2017.
 */
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        registerEndpoints();
    }

    private void registerEndpoints() {
        register(Heroes.class);
        register(ExceptionProvider.class);
    }
}