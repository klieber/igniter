package com.dsmhack.igniter.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GoogleDriveIntegrationStepDefinitions  {

    @Then("^The google Drive folder of \"([^\"]*)\" exists$")
    public void theGoogleDriveFolderOfExists(String teamName) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^The User is Added$")
    public void theUserIsAdded() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @Then("^The google drive folder for \"([^\"]*)\" should have the user \"([^\"]*)\" added with read rights$")
    public void theGoogleDriveFolderForShouldHaveTheUserAddedWithReadRights(String teamName, String googleEmail) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @And("^The admin created a \"([^\"]*)\"$")
    public void theAdminCreatedA(String teamSpecificFolderPath) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^The integration service googleDrive is configured$")
    public void theIntegrationServiceGoogleDriveIsConfigured() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
