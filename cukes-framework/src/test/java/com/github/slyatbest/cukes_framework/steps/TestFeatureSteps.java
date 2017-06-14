package com.github.slyatbest.cukes_framework.steps;

import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TestFeatureSteps
{
    @Given("^I have a product$")
    public void iHaveAProduct() throws Throwable
    {
        //Do nothing
    }

    @When("^I ask it to do something$")
    public void iAskItToDoSomething() throws Throwable
    {
        //Do nothing
    }

    @Then("^something useful happens$")
    public void somethingUsefulHappens() throws Throwable
    {
        Assert.fail("Something went wrong!");
    }
}
