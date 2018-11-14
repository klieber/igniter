package com.dsmhack.igniter.services;

import lombok.Builder;
import lombok.Singular;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class TeamResult {

  @Singular
  private List<IntegrationResult> integrations;

  public boolean isSuccess() {
    return integrations.stream().allMatch(IntegrationResult::isSuccess);
  }

  public boolean isFailure() {
    return integrations.stream().allMatch(IntegrationResult::isFailure);
  }

  public boolean isPartialFailure() {
    return !isSuccess() && !isFailure();
  }

  public List<IntegrationResult> getSuccesses() {
    return integrations.stream().filter(IntegrationResult::isSuccess).collect(Collectors.toList());
  }

  public List<IntegrationResult> getFailures() {
    return integrations.stream().filter(IntegrationResult::isFailure).collect(Collectors.toList());
  }
}
