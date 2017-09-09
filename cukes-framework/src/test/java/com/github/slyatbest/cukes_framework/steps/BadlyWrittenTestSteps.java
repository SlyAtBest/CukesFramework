package com.github.slyatbest.cukes_framework.steps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Assert;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BadlyWrittenTestSteps
{
    final List<String> failures = new ArrayList<String>(
            Arrays.asList("An element wasn't selectable", "The test wasn't in the desired state",
                    "Something went wrong", "You failed step one", "Stop doing it wrong", "Environment issue",
                    "It's too hot", "It's too cold", "Something timed out", "Your product is untestable"));

    @When("^I test a feature badly$")
    public void iTestAFeatureBadly() throws Throwable
    {
        //Do nothing
    }

    @Then("^something bad happens$")
    public void somethingBadHappens() throws Throwable
    {
        Random rand = new Random();
        int randomNumber = rand.nextInt((9 - 0) + 1) + 0;
        Assert.fail(failures.get(randomNumber));

    }

}
