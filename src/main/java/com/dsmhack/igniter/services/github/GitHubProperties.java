package com.dsmhack.igniter.services.github;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GitHubProperties {
    private boolean enabled;
    private String oauthKey;
    private String clientId;
    private String secretKey;
    private String orgName;
    private String prefix;

}
