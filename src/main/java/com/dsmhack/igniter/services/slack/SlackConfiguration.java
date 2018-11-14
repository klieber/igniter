package com.dsmhack.igniter.services.slack;

import com.github.seratch.jslack.Slack;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackConfiguration {

    @Bean
    @ConfigurationProperties("igniter.slack")
    public SlackProperties slackProperties() {
        return new SlackProperties();
    }

    @Bean
    public SlackIntegrationService slackIntegrationService() {
        return new SlackIntegrationService(slackProperties(), slack());
    }

    @Bean
    public Slack slack() {
        return Slack.getInstance();
    }
}
