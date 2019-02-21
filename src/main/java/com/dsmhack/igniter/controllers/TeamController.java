package com.dsmhack.igniter.controllers;

import com.dsmhack.igniter.services.TeamConfigurationServiceFactory;
import com.dsmhack.igniter.util.TeamUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamController {

  private final TeamConfigurationServiceFactory teamConfigurationService;

  public TeamController(TeamConfigurationServiceFactory teamConfigurationService) {
    this.teamConfigurationService = teamConfigurationService;
  }

  @GetMapping
  public String teamList(Model model) {
    model.addAttribute("teams", teamConfigurationService.create().getTeams());
    model.addAttribute("teamPrefix", TeamUtils.getDefaultPrefix());
    return "teams";
  }

  @PostMapping
  public String createTeams(@RequestParam("teamPrefix") String teamPrefix,
                            @RequestParam("numberOfTeams") int numberOfTeams,
                            RedirectAttributes redirectAttributes) {

    List<String> teams = teamConfigurationService.create().createTeams(teamPrefix, numberOfTeams);

    redirectAttributes.addFlashAttribute(
        "message",
        "You successfully created " + teams.size() + " teams."
    );

    redirectAttributes.addFlashAttribute("teams", teams);

    return "redirect:/teams";
  }
}
