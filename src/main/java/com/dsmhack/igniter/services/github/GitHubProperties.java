package com.dsmhack.igniter.services.github;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GitHubProperties {
  private String oAuthKey;
  private String orgName;
  private String prefix;
}
