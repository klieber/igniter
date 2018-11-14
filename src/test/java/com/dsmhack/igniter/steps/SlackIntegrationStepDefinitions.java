package com.dsmhack.igniter.steps;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.groups.GroupsCreateRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsListRequest;
import com.github.seratch.jslack.api.methods.response.groups.GroupsListResponse;
import com.github.seratch.jslack.api.model.Group;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.assertj.core.util.Lists.list;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SlackIntegrationStepDefinitions {

  @Autowired
  private Slack slack;

  @Then("^The Slack Channel of \"([^\"]*)\" is created")
  public void theSlackChannelIsCreated(String team) throws IOException, SlackApiException {
    verify(slack.methods()).groupsCreate(isA(GroupsCreateRequest.class));
  }

  @Given("^The Slack Channel of \"([^\"]*)\" exists")
  public void theSlackChannelOfExists(String team) throws IOException, SlackApiException {
    GroupsListResponse groupsListResponse = Mockito.mock(GroupsListResponse.class);
    Group group = Mockito.mock(Group.class);

    when(slack.methods().groupsList(isA(GroupsListRequest.class))).thenReturn(groupsListResponse);
    when(groupsListResponse.getGroups()).thenReturn(list(group));
    when(group.getName()).thenReturn(team);
  }

  @Given("^the user is a member of \"([^\"]*)\" slack channel")
  public void theUserHasBeenAdded(String team) throws IOException, SlackApiException {
    GroupsListResponse groupsListResponse = Mockito.mock(GroupsListResponse.class);
    Group group = Mockito.mock(Group.class);

    when(slack.methods().groupsList(isA(GroupsListRequest.class))).thenReturn(groupsListResponse);
    when(groupsListResponse.getGroups()).thenReturn(list(group));
    when(group.getName()).thenReturn(team);
  }
}