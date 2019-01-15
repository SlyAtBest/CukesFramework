package com.github.slyatbest.cukes_framework.elasticsearch;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import cucumber.api.Result;
import cucumber.api.TestCase;
import cucumber.api.TestStep;
import cucumber.runner.PickleTestStep;
import cucumber.runner.UnskipableStep;

/**
 *  POJO to represent a single step result that can easily be converted to JSON and pushed out to Elasticsearch
 */
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

    /**
     * Constructor
     * 
     * @param testStep {@link TestStep}
     * @param result {@link Result}
     * @param testCase {@link TestCase}
     */
    public ElasticStepResult(TestStep testStep, Result result, TestCase testCase)
    {
        this.result = result.getStatus().toString().toLowerCase(Locale.getDefault());
        this.reason = result.getErrorMessage();
        this.feature = returnFeatureNameFromURI(testCase);
        this.scenario = testCase.getName();
        this.duration = returnDurationInSeconds(result);
        this.created = createTimeStamp();
        this.branch = System.getenv("BRANCH_NAME");
        this.product = System.getenv("PRODUCT");
        this.tags = returnTags(testCase);
        this.step = returnStepOrHookText(testStep);
    }

    /**
     * Method to retrieve the step or hook text, return null if the TestStep is neither a PickleStep or an Unskipable
     * @param testStep {@link Test}
     * @return The step or hook text, or null if it's unexpected TestStep type
     */
    private String returnStepOrHookText(TestStep testStep)
    {
        if (testStep instanceof PickleTestStep)
        {
            PickleTestStep pickleTestStep = (PickleTestStep) testStep;
            return pickleTestStep.getStepText();
        }

        if (testStep instanceof UnskipableStep)
        {
            UnskipableStep unskipableStep = (UnskipableStep) testStep;
            return unskipableStep.getCodeLocation();
        }

        return null;
    }

    /**
     * Method to retrieve the scenario tags, return null if no tags are found
     * @param testCase {@link TestCase}
     * @return List<String> of tags, or null if none are found
     */
    private List<String> returnTags(TestCase testCase)
    {
        List<String> tags = new ArrayList<String>();
        testCase.getTags().stream().forEach((t) -> tags.add(t.getName()));

        if (tags.isEmpty())
        {
            return null;
        }

        return tags;
    }

    /**
     * Method to return a time stamp in the format yyyy/MM/dd HH:mm:ss
     * @return String containing a timestamp in the desired format
     */
    private String createTimeStamp()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Method to return the duration in seconds
     * @param result {@link Result}
     * @return double containing duration in seconds
     */
    private double returnDurationInSeconds(Result result)
    {
        Long duration = Optional.ofNullable(result.getDuration()).orElse(0L);

        double durationSeconds = (double) duration / 1000000000L;
        return Math.round(durationSeconds * 100.0) / 100.0;
    }

    /**
     * Method to retrieve the feature name from the full feature URI
     * @param testCase {@link TestCase}
     * @return String containing the feature name
     */
    private String returnFeatureNameFromURI(TestCase testCase)
    {
        Path featureUri = Optional.ofNullable(Paths.get(testCase.getUri())).orElseThrow(() -> new IllegalStateException(
                "The feature URI does not match the expected format and returned null"));

        Path filename = Optional.ofNullable(featureUri.getFileName()).orElseThrow(() -> new IllegalStateException(
                String.format("Unable to extract filename from feature URI: %s", featureUri)));

        return filename.toString();
    }
}
