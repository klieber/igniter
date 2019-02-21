package com.dsmhack.igniter.models;

import lombok.*;

import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Team {

  private final String name;

  @Singular
  private final List<User> users;
}
