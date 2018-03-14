package com.dsmhack.igniter.services.github;

import com.dsmhack.igniter.configuration.IntegrationServicesConfiguration;
import com.dsmhack.igniter.services.IntegrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.Team;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.TeamService;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GitHubIntegrationService implements IntegrationService {

    private final IntegrationServicesConfiguration integrationServicesConfiguration;

    final ObjectMapper objectMapper;
    private GitHubClient gitHubClient;
    private GitHubConfig gitHubConfig;
    private GitHub gitHubService;
    private GHOrganization organization;


    @Autowired
    public GitHubIntegrationService(ObjectMapper objectMapper, IntegrationServicesConfiguration integrationServicesConfiguration) {
        this.objectMapper = objectMapper;
        this.integrationServicesConfiguration = integrationServicesConfiguration;
    }

    @Override
    public String getIntegrationServiceName() {
        return "gitHub";
    }

    @Override
    public void createTeam(String teamName) {
        GHRepository ghRepository;
        GHTeam team;
        try {
            ghRepository = buildOrGetRepository(teamName);
            team = buildOrGetTeam(teamName, ghRepository);
            team.add(ghRepository, GHOrganization.Permission.PUSH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Map<String, String> validateTeamName(String team) {
        return null;
    }

    private GHTeam buildOrGetTeam(String teamName, GHRepository ghRepository) throws IOException {
        GHTeam team = getTeam(teamName);
        if (team == null) {
            createTeam(gitHubConfig.getOrgName(), teamName, Collections.singletonList(ghRepository.getFullName()));
            team = getTeam(teamName);
        }
        return team;
    }

    private GHTeam getTeam(String teamName) throws IOException {
        return organization.getTeamByName(teamName);
    }

    public Team createTeam(String organization, String teamName, List<String> repoNames) {
        Team team = new Team();
        team.setPermission("admin");
        team.setName(teamName);
        StringBuilder uri = new StringBuilder("/orgs");
        uri.append('/').append(organization);
        uri.append("/teams");
        Map<String, Object> params = new HashMap();
        params.put("name", team.getName());
        params.put("permission", team.getPermission());
        params.put("privacy", "closed");
        if (repoNames != null) {
            params.put("repo_names", repoNames);
        }
        try {
            return (Team) gitHubClient.post(uri.toString(), params, Team.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }





    private GHRepository buildOrGetRepository(String teamName) throws IOException {
        GHRepository repository = getRepositoryIfExists(teamName);
        if (repository == null) {
            repository = organization.createRepository(teamName).description("This is the repo for the '" + gitHubConfig.getPrefix() + "' event for the team:'" + teamName + "'").create();
        }
        return repository;
    }

    private GHRepository getRepositoryIfExists(String teamName) throws IOException {
        return organization.getRepository(teamName);
    }

    @PostConstruct
    public void configure() throws IOException {
        File file = Paths.get(integrationServicesConfiguration.getKeyPath(), "git-hub-credentials.json").toFile();
        gitHubConfig = objectMapper.readerFor(GitHubConfig.class).readValue(file);
        gitHubService = new GitHubBuilder().withOAuthToken(gitHubConfig.getOAuthKey(), gitHubConfig.getOrgName()).build();
        organization = gitHubService.getOrganization(gitHubConfig.getOrgName());
        gitHubClient = new GitHubClient();
        gitHubClient.setOAuth2Token(gitHubConfig.getOAuthKey());
    }

}
