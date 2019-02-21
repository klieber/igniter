package com.dsmhack.igniter.controllers;

import com.dsmhack.igniter.services.user.UserFormat;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserForm {
  private MultipartFile file;
  @NotEmpty
  private String teamPrefix;
  private int numberOfTeams;
  private UserFormat userFormat;
  private List<String> integrations = new ArrayList<>();
}
