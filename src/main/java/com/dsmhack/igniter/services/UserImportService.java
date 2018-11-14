package com.dsmhack.igniter.services;

import com.dsmhack.igniter.models.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserImportService {

  public List<User> getUsers(String filepath) {
    return this.getUserLinesFromFile(filepath).stream()
               .skip(1)
               .map(this::parseStringIntoUser)
               .collect(Collectors.toList());
  }

  private List<String> getUserLinesFromFile(String filePath) {
    return getUserLinesFromPath(Paths.get(filePath));
  }

  private List<String> getUserLinesFromPath(Path path) {
    List<String> output;
    try (Stream<String> linesStream = Files.lines(path)) {
      output = linesStream.collect(Collectors.toList());
    } catch (IOException e) {
      output = Collections.emptyList();
    }
    return output;
  }


  private User parseStringIntoUser(String userInfo) {
    String[] userParts = userInfo.split(",");
    return new User(userParts[0], userParts[1], userParts[2], userParts[3]);
  }
}
