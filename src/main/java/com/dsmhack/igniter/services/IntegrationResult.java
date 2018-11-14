package com.dsmhack.igniter.services;

import lombok.Value;

@Value
public class IntegrationResult {

  public enum StatusType {
    SUCCESS,
    FAILURE
  }

  private String name;
  private StatusType status;
  private String message;

  public boolean isSuccess() {
    return StatusType.SUCCESS.equals(status);
  }

  public boolean isFailure() {
    return StatusType.FAILURE.equals(status);
  }

  public static IntegrationResult success(String name) {
    return success(name,"Success.");
  }

  public static IntegrationResult success(String name, String message) {
    return new IntegrationResult(name, StatusType.SUCCESS, message);
  }
  public static IntegrationResult failure(String name, String message) {
    return new IntegrationResult(name, StatusType.FAILURE, message);
  }
}
