package com.dsmhack.igniter.steps;

import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.IntegrationServicesRegistry;
import com.dsmhack.igniter.services.TeamConfigurationService;
import com.dsmhack.igniter.services.github.GitHubProperties;
import com.dsmhack.igniter.services.slack.SlackProperties;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static junit.framework.TestCase.assertTrue;

public class SharedStepDefinitions {

  @Autowired
  private TeamConfigurationService teamConfigurationService;

  @Autowired
  IntegrationServicesRegistry integrationServicesRegistry;

  @Autowired
  private SlackProperties slackProperties;

  @Autowired
  private GitHubProperties gitHubProperties;

  private User user;

  @When("^The Admin creates the team \"([^\"]*)\"$")
  public void theAdminCreatesTheTeam(String teamName) {
    // Write code here that turns the phrase above into concrete actions
    teamConfigurationService.createTeam(teamName);
  }

  @When("^The following user is added$")
  public void theFollowingUserIsAdded() {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @When("^The following user is staged$")
  public void theFollowingUserIsStaged(DataTable dataTable) {
    Map<String, String> userProperties = dataTable.asMaps().get(0);

    this.user = new User(
        userProperties.get("firstName"),
        userProperties.get("lastName"),
        userProperties.get("email"),
        userProperties.get("githubUsername")
    );
  }

  @When("^the Admin assigns the user to \"([^\"]*)\"$")
  public void theAdminAssignsTheUserTo(String teamName) {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @Given("^The integration service \"([^\"]*)\" is enabled$")
  public void theIntegrationServiceIsEnabled(String name) {
    this.slackProperties.setEnabled("slack".equals(name));
    this.gitHubProperties.setEnabled("github".equals(name));
  }

  @Then("^The active integration services contain \"([^\"]*)\"$")
  public void theOnlyActiveIntegrationServiceIs(String integrationServiceName) {
    assertTrue(integrationServicesRegistry.getActiveIntegrationServices()
                   .stream()
                   .anyMatch(integrationService -> integrationService.getName().equals(integrationServiceName)));
  }


  @And("^The staged user is added to the team \"([^\"]*)\"$")
  public void theStagedUserIsAddedToTheTeam(String teamName) {
    teamConfigurationService.addUserToTeam(teamName, this.user);
  }
}
