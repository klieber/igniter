package com.dsmhack.igniter.controllers;

import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.IntegrationServicesRegistry;
import com.dsmhack.igniter.services.TeamConfigurationService;
import com.dsmhack.igniter.services.TeamConfigurationServiceFactory;
import com.dsmhack.igniter.services.user.UserImportException;
import com.dsmhack.igniter.services.user.UserImportService;
import com.dsmhack.igniter.services.user.UserImportServiceRegistry;
import com.dsmhack.igniter.util.TeamUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

  private final UserImportServiceRegistry userImportServiceRegistry;
  private final IntegrationServicesRegistry integrationServicesRegistry;
  private final TeamConfigurationServiceFactory teamConfigurationServiceFactory;

  public UserController(UserImportServiceRegistry userImportServiceRegistry,
                        IntegrationServicesRegistry integrationServicesRegistry,
                        TeamConfigurationServiceFactory teamConfigurationServiceFactory) {
    this.userImportServiceRegistry = userImportServiceRegistry;
    this.integrationServicesRegistry = integrationServicesRegistry;
    this.teamConfigurationServiceFactory = teamConfigurationServiceFactory;
  }

  @GetMapping
  public String userImportForm(Model model) {
    model.addAttribute("teamPrefix", TeamUtils.getDefaultPrefix());
    model.addAttribute("userFormats", userImportServiceRegistry.getSupportedFormats());
    model.addAttribute("integrations", integrationServicesRegistry.getSupportedIntegrations());
    return "userImport";
  }

  @PostMapping
  public String uploadUsers(@Validated UserForm userForm,
                            RedirectAttributes redirectAttributes) throws IOException, UserImportException {

    UserImportService userImportService = userImportServiceRegistry.getService(userForm.getUserFormat())
        .orElseThrow(() -> new RuntimeException("User format not supported: " + userForm.getUserFormat()));

    List<User> users = userImportService.getUsers(new InputStreamReader(userForm.getFile().getInputStream()));

    redirectAttributes.addFlashAttribute(
        "message",
        "You successfully uploaded " + users.size() + " users from " + userForm.getFile().getOriginalFilename()
    );
    TeamConfigurationService teamConfigurationService = teamConfigurationServiceFactory.create(userForm.getIntegrations());

//    teamConfigurationService.createTeams(userForm.getTeamPrefix(), userForm.getNumberOfTeams())
//        .forEach(teamName -> teamConfigurationService.addUsersToTeam(teamName, users));

    return "redirect:/users";
  }

}
