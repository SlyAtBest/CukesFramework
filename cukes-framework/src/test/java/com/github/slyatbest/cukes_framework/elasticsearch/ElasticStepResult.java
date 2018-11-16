package com.github.slyatbest.cukes_framework.elasticsearch;

import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import cucumber.api.Result;
import cucumber.api.TestCase;
import cucumber.api.TestStep;
import cucumber.runner.PickleTestStep;
import cucumber.runner.UnskipableStep;

@SuppressWarnings("unused")
public class ElasticStepResult
{
    private final String feature;
    private final String scenario;
    private final String result;
    private final String reason;
    private final double duration;
    private final String created;
    private final String branch;
    private final String product;
    private List<String> tags;
    private String step;

    public ElasticStepResult(TestStep testStep, Result result, TestCase testCase, Properties properties)
    {
        this.result = result.getStatus().toString().toLowerCase(Locale.getDefault());
        this.reason = result.getErrorMessage();

        this.feature = Paths.get(testCase.getUri()).getFileName().toString();
        this.scenario = testCase.getName();

        double durationSeconds = (double) result.getDuration() / 1000000000L;
        this.duration = Math.round(durationSeconds * 100.0) / 100.0;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.created = dateFormat.format(date);

        this.branch = properties.getProperty("branch");
        this.product = properties.getProperty("product");

        if (!testCase.getTags().isEmpty())
        {
            tags = new ArrayList<String>();
            testCase.getTags().stream().forEach((t) -> this.tags.add(t.getName()));
        }

        if (testStep instanceof PickleTestStep)
        {
            PickleTestStep pickleTestStep = (PickleTestStep) testStep;
            this.step = testStep.getStepText();
        }
        else if (testStep instanceof UnskipableStep)
        {
            UnskipableStep unskipableStep = (UnskipableStep) testStep;
            this.step = unskipableStep.getCodeLocation();
        }
    }
}
