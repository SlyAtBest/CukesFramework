package com.github.slyatbest.cukes_framework.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class FailingBackgroundTestSteps
{
    @Given("^my background step fails$")
    public void myBackgroundStepFails() throws Exception
    {
        throw new PendingException();
    }

    @Then("^this entire scenario should be skipped$")
    public void thisEntireScenarioShouldBeSkipped() throws Exception
    {
        // do nothing
    }
}
