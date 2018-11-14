package com.dsmhack.igniter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntegrationServicesRegistry {

  private final List<IntegrationService> availableServices;

  @Autowired
  public IntegrationServicesRegistry(List<IntegrationService> availableServices) {
    this.availableServices = availableServices;
  }

  public List<IntegrationService> getActiveIntegrationServices() {
    return this.availableServices.stream()
        .filter(IntegrationService::isEnabled)
        .collect(Collectors.toList());
  }
}
