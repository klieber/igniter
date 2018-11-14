package com.dsmhack.igniter.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamProperties {
    private String teamPrefix;
    private Integer teamCount;

    public String getCompositeName(String teamName) {
        return this.teamPrefix + teamName;
    }
}
