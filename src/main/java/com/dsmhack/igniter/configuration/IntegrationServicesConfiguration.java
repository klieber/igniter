package com.dsmhack.igniter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntegrationServicesConfiguration {

    public IntegrationServicesConfiguration() {
    }

    @Bean
    @ConfigurationProperties("igniter.team")
    public TeamProperties teamProperties() {
        return new TeamProperties();
    }
}
