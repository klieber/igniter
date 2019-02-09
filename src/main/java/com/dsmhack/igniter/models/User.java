package com.dsmhack.igniter.models;

import lombok.*;

import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private final String lastName;
    private final String firstName;
    private final String email;
    private final String slackEmail;
    private final String githubUsername;

    @Singular
    private List<Team> teams;
}
