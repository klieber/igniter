package com.dsmhack.igniter.services;

import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.exceptions.IntegrationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
public class TeamConfigurationService {

  private final IntegrationServicesRegistry integrationServicesRegistry;

  @Autowired
  public TeamConfigurationService(IntegrationServicesRegistry integrationServicesRegistry) {
    this.integrationServicesRegistry = integrationServicesRegistry;
  }

  public TeamResult createTeam(String teamName) {

    return TeamResult.builder()
        .integrations(
            this.integrationServicesRegistry.getActiveIntegrationServices().stream()
                .map(integrationService -> createTeam(integrationService, teamName))
                .collect(Collectors.toList())
        )
        .build();
  }

  public TeamResult addUserToTeam(String teamName, User user) {
    return TeamResult.builder()
        .integrations(
            this.integrationServicesRegistry.getActiveIntegrationServices().stream()
                .map(integrationService -> addUserToTeam(integrationService, teamName, user))
                .collect(Collectors.toList())
        )
        .build();
  }

  private IntegrationResult createTeam(IntegrationService integrationService, String teamName) {
    IntegrationResult status;
    try {
      LOGGER.info("Creating team: {}", teamName);
      integrationService.createTeam(teamName);
      status = IntegrationResult.success(integrationService.getName());
    } catch (IntegrationException e) {
      status = IntegrationResult.failure(integrationService.getName(), e.getMessage());
    }
    return status;
  }

  private IntegrationResult addUserToTeam(IntegrationService integrationService, String teamName, User user) {
    IntegrationResult result;
    try {
      LOGGER.info("Adding User: {} teamName: {}", user.getEmail(), teamName);
      integrationService.addUserToTeam(teamName, user);
      result = IntegrationResult.success(integrationService.getName());
    } catch (IntegrationException e) {
      result = IntegrationResult.failure(integrationService.getName(), ExceptionUtils.getStackTrace(e));
    }
    return result;
  }
}
