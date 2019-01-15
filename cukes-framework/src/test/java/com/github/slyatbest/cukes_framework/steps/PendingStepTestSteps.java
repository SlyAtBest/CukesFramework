package com.github.slyatbest.cukes_framework.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Then;

public class PendingStepTestSteps
{
    @Then("^throw a pending exception$")
    public void throw_a_pending_exception() throws Exception
    {
        throw new PendingException();
    }
}
