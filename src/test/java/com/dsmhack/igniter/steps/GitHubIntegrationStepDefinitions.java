package com.dsmhack.igniter.steps;

import com.dsmhack.igniter.configuration.IntegrationServicesConfiguration;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

public class GitHubIntegrationStepDefinitions  {

    @Autowired
    IntegrationServicesConfiguration integrationServicesConfiguration;


    @Then("^The gitGub repo of \"([^\"]*)\" exists$")
    public void theGitGubRepoOfExists(String teamName) {
        // Write code here that turns the phrase above into concrete actions

    }
}
