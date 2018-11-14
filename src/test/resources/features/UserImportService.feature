Feature: A config file is loaded and data is supplied

  Scenario: Load users from a file
    Given a users file named "exampleUser.csv" exists
    When I get a list of users from the "exampleUser.csv" file
    Then I should have a list containing the following users
      | lastName | firstName   | email                 | githubUsername    |
      | doe      | john        | anEmail               | aGithubUserName   |
      | Rolek    | Stewie      | stewie@email.com      | stewieGithub      |
      | Rolek    | LilSquiggle | lilsquiggle@email.com | lilsquiggleGithub |
