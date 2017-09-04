package com.github.slyatbest.cukes_framework;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources", tags = {"~@ignore"},
        plugin = {"pretty", "json:target/cucumber-report.json"}, snippets = SnippetType.CAMELCASE, strict = true)
public class CukesRunTest
{

}
