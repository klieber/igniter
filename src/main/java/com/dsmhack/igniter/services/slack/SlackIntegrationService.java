package com.dsmhack.igniter.services.slack;

import com.dsmhack.igniter.models.TeamValidation;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.IntegrationService;
import com.dsmhack.igniter.services.exceptions.ActionNotRequiredException;
import com.dsmhack.igniter.services.exceptions.DataConfigurationException;
import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.groups.GroupsCreateRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsInviteRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsKickRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsListRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersLookupByEmailRequest;
import com.github.seratch.jslack.api.methods.response.channels.UsersLookupByEmailResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsCreateResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsInviteResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsListResponse;
import com.github.seratch.jslack.api.model.Group;

import java.io.IOException;

import static java.lang.String.format;

public class SlackIntegrationService implements IntegrationService {

  private final Slack slack;
  private final SlackProperties slackProperties;

  public SlackIntegrationService(SlackProperties slackProperties, Slack slack) {
    this.slack = slack;
    this.slackProperties = slackProperties;
  }

  @Override
  public String getName() {
    return "slack";
  }

  @Override
  public boolean isEnabled() {
    return this.slackProperties.isEnabled();
  }

  @Override
  public void createTeam(String teamName) throws DataConfigurationException {
    System.out.println("creating a slack team");
    GroupsCreateResponse groupsCreateResponse;
    try {
      if (getGroupIfExists(teamName) != null) {
        throw new ActionNotRequiredException("Group with name'%s' already exists in slack");
      }
      groupsCreateResponse = slack.methods()
          .groupsCreate(GroupsCreateRequest.builder()
                            .name(teamName)
                            .token(slackProperties.getOauthKey())
                            .build());
    } catch (Exception e) {
      throw new DataConfigurationException(String.format("Failed to create slack group of name:'%s'", teamName), e);
    }
    if (!groupsCreateResponse.isOk()) {
      throw new DataConfigurationException(String.format("Failed to create slack group of name:'%s'. Error was:%s",
                                                         teamName,
                                                         groupsCreateResponse.getError()));
    }


  }

  @Override
  public TeamValidation validateTeam(String team) throws DataConfigurationException {
    TeamValidation teamValidation = new TeamValidation();

    try {
      Group group = getGroup(team);
      teamValidation.setTeamName(group.getName());
      teamValidation.getAncilaryDetails().put("TeamConfiguration", group.toString());
      teamValidation.setMembers(group.getMembers());
      return teamValidation;
    } catch (SlackApiException | IOException e) {
      throw new DataConfigurationException("Cannot Validate Team:'%s' ", e);
    }
  }

  @Override
  public void addUserToTeam(String compositeName, User user) throws DataConfigurationException {

    try {
      Group group = getGroup(compositeName);
      com.github.seratch.jslack.api.model.User userLookup = getUserByEmail(user);
      GroupsInviteRequest groupsInviteRequest = GroupsInviteRequest.builder()
          .channel(group.getName())
          .token(slackProperties.getOauthKey())
          .user(userLookup.getId())
          .build();
      GroupsInviteResponse groupsInviteResponse = slack.methods().groupsInvite(groupsInviteRequest);
      if (!groupsInviteResponse.isOk()) {
        throw new DataConfigurationException(format("Error adding user '%s' to team '%s'. Error was:%s",
                                                    user.getEmail(),
                                                    compositeName,
                                                    groupsInviteResponse.getError()));
      }
    } catch (SlackApiException | IOException e) {
      throw new DataConfigurationException(format("Error adding user '%s' to team '%s'",
                                                  user.getEmail(),
                                                  compositeName), e);
    }
  }

  @Override
  public void removeUserFromTeam(String teamName, User user) throws DataConfigurationException {
    try {
      Group group = getGroup(teamName);
      com.github.seratch.jslack.api.model.User userByEmail = getUserByEmail(user);
      slack.methods()
          .groupsKick(GroupsKickRequest.builder()
                          .channel(group.getId())
                          .user(userByEmail.getId())
                          .token(slackProperties.getOauthKey())
                          .build());
    } catch (SlackApiException | IOException e) {
      throw new DataConfigurationException(format("Error removing user '%s' from team '%s'", user.getEmail(), teamName),
                                           e);
    }
  }

  private com.github.seratch.jslack.api.model.User getUserByEmail(User user) throws DataConfigurationException, IOException {
    UsersLookupByEmailRequest userLookup = UsersLookupByEmailRequest.builder()
        .token(slackProperties.getOauthKey())
        .email(user.getEmail())
        .build();
    try {
      UsersLookupByEmailResponse usersLookupByEmailResponse = slack.methods().usersLookupByEmail(userLookup);
      if (!usersLookupByEmailResponse.isOk()) {
        throw new DataConfigurationException(String.format("Error Fetching slack user with email:'%s'. Error: %s",
                                                           user.getEmail(),
                                                           usersLookupByEmailResponse.getError()));
      }
      return usersLookupByEmailResponse.getUser();
    } catch (SlackApiException e) {
      throw new DataConfigurationException(format("Error fetching user for email: %s", user.getEmail()), e);
    }

  }

  private Group getGroup(String teamName) throws IOException, SlackApiException, DataConfigurationException {
    Group group = getGroupIfExists(teamName);
    if (group == null) {
      throw new DataConfigurationException(format("There is no channel with a name of '%s'", teamName));
    }
    return group;
  }

  private Group getGroupIfExists(String compositeName) throws IOException, SlackApiException {
    GroupsListRequest groupsListRequest = GroupsListRequest.builder().token(slackProperties.getOauthKey()).build();
    GroupsListResponse groupsListResponse = slack.methods().groupsList(groupsListRequest);
    return groupsListResponse.getGroups()
        .stream()
        .filter(g -> g.getName().equals(compositeName))
        .findFirst()
        .orElse(null);
  }
}
