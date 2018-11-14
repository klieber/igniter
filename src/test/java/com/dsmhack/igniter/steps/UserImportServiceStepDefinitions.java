package com.dsmhack.igniter.steps;

import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.UserImportService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;

public class UserImportServiceStepDefinitions {

  @Autowired
  private UserImportService userImportService;
  private String usersFile;
  private List<User> users;

  @Given("^a users file named \"exampleUser.csv\" exists$")
  public void usersFileExists() throws URISyntaxException {
    URL resource = UserImportServiceStepDefinitions.class.getResource("/exampleUser.csv");
    assertThat(resource).isNotNull();
    usersFile = new File(resource.toURI()).getAbsolutePath();
  }

  @When("^I get a list of users from the \"exampleUser.csv\" file$")
  public void getAListOfUsers() {
    this.users = userImportService.getUsers(usersFile);
  }

  @Then("I should have a list containing the following users")
  public void shouldHaveListContaining(DataTable expectedUsers) {
    List<Map<String, String>> maps = expectedUsers.asMaps();

    assertThat(users).hasSize(maps.size());

    for (int i = 0; i < maps.size(); i++) {
      final int index = i;
      assertThat(users)
          .satisfies(user -> validateObject(user, maps.get(index)), atIndex(index));
    }

  }

  private <E> void validateObject(E element, Map<String, String> expected) {
    String[] fields = expected.keySet().toArray(new String[0]);
    assertThat(element)
        .extracting(fields)
        .containsExactly(Arrays.stream(fields).map(expected::get).toArray());
  }

}
