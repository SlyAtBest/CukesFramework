package com.github.slyatbest.cukes_framework.steps;

import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class ScenarioOutlineTestSteps
{
    @Given("^I test the first value$")
    public void iTestTheFirstValue() throws Throwable
    {
        Thread.sleep(1000);
    }

    @Given("^I test the second value$")
    public void iTestTheSecondValue() throws Throwable
    {
        Thread.sleep(2000);
    }

    @Given("^I test the third value$")
    public void iTestTheThirdValue() throws Throwable
    {
        Thread.sleep(3000);
    }

    @Then("^the results should be visible in the results$")
    public void theResultsShouldBeVisibleInTheResults() throws Throwable
    {
        Assert.assertTrue(true);
    }
}
