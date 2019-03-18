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
        Thread.sleep(1000);
    }

    @When("^I ask it to do something$")
    public void iAskItToDoSomething() throws Throwable
    {
        Thread.sleep(2000);
    }

    @Then("^something useful happens$")
    public void somethingUsefulHappens() throws Throwable
    {
        Thread.sleep(1000);
        Assert.assertTrue("Something happened", true);
    }

    @Given("^I complete another background step$")
    public void iCompleteAnotherBackgroundStep() throws Exception
    {
        Thread.sleep(1000);
    }

    @Given("^I complete one further background step$")
    public void iCompleteOnFurtherBackgroundStep() throws Exception
    {
        Thread.sleep(1000);
    }
}
