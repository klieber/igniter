package com.dsmhack.igniter;

import com.dsmhack.igniter.configuration.TeamProperties;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.TeamConfigurationService;
import com.dsmhack.igniter.services.UserImportService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@Service
public class BatchRunner {

    private final UserImportService userImportService;
    private final TeamProperties teamProperties;
    private final TeamConfigurationService teamConfigurationService;

    @Autowired
    public BatchRunner(TeamConfigurationService teamConfigurationService,
                       UserImportService userImportService,
                       TeamProperties teamProperties) {
        this.teamConfigurationService = teamConfigurationService;
        this.userImportService = userImportService;
        this.teamProperties = teamProperties;
    }

    public void onboardEveryone(String fileName) {
        System.out.println("onboard.filename: " + fileName);
        List<User> usersByList = userImportService.getUsers(fileName);
        for (Integer i = 1; i <= teamProperties.getTeamCount(); i++) {
            String compositeName = teamProperties.getCompositeName(i.toString());
            System.out.println("compositeName: " + compositeName);
            teamConfigurationService.createTeam(compositeName);
            usersByList.forEach(user ->{ teamConfigurationService.addUserToTeam(compositeName, user); } );
        }
    }
}
