package com.dsmhack.igniter.util;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class TeamUtils {

  public static String getDefaultPrefix() {
    return LocalDate.now().getYear() + "-team-";
  }

  public static List<String> fillTeams(String teamPrefix, int numberOfTeams) {
    return IntStream.rangeClosed(1, numberOfTeams)
        .mapToObj(teamId -> String.format("%s%02d", teamPrefix, teamId))
        .collect(Collectors.toList());
  }
}
