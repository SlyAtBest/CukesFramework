package com.github.slyatbest.cukes_framework;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources", tags = {" not @ignore"},
        plugin = {"pretty",
                "com.github.slyatbest.cukes_framework.elasticsearch.ElasticFormatter:target/elastic-output.json",
                "json:target/cucumber-report.json"},
        snippets = SnippetType.CAMELCASE, strict = false)
public class CukesRunTest
{

}
