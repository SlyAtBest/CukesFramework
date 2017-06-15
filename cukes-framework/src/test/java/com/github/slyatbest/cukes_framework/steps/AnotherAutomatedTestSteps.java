package com.github.slyatbest.cukes_framework.steps;

import org.junit.Assert;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AnotherAutomatedTestSteps
{
    @When("^I test another feature$")
    public void iTestAnotherFeature() throws Throwable
    {
        // Do nothing
    }

    @Then("^something else happens$")
    public void somethingElseHappens() throws Throwable
    {
        Assert.assertTrue("Something else happens", true);
    }
}
