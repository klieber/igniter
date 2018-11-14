package com.dsmhack.igniter.services.github;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GitHubConfiguration {

    @Bean
    @ConfigurationProperties("igniter.github")
    public GitHubProperties gitHubProperties() {
        return new GitHubProperties();
    }

    @Bean
    public GitHubIntegrationService gitHubIntegrationService() throws IOException {
        return new GitHubIntegrationService(gitHubProperties(), gitHubService(), gitHubClient());
    }

    @Bean
    public GitHub gitHubService() throws IOException {
        return new GitHubBuilder()
                .withOAuthToken(gitHubProperties().getOauthKey(), gitHubProperties().getOrgName())
                .build();
    }

    @Bean
    public GitHubClient gitHubClient() {
        GitHubClient gitHubClient = new GitHubClient();
        gitHubClient.setOAuth2Token(gitHubProperties().getOauthKey());
        return gitHubClient;
    }

}
