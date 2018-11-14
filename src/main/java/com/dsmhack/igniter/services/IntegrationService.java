package com.dsmhack.igniter.services;

import com.dsmhack.igniter.models.TeamValidation;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.exceptions.IntegrationException;

public interface IntegrationService {
  String getName();

  boolean isEnabled();

  void createTeam(String teamName) throws IntegrationException;

  TeamValidation validateTeam(String team) throws IntegrationException;

  void addUserToTeam(String teamName, User user) throws IntegrationException;

  void removeUserFromTeam(String teamName, User user) throws IntegrationException;
}
