package com.github.slyatbest.cukes_framework.elasticsearch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.Step;

@SuppressWarnings("unused")
public class ElasticStepResult
{
    private final String feature;
    private final String scenario;
    private final String keyword;
    private final String step;
    private final int lineNumber;
    private final String result;
    private final String reason;
    private final String classname;
    private final double duration;
    private final String created;
    private final String branch;
    private final String product;

    public ElasticStepResult(Step step, Result result, Match match, Feature feature, Scenario scenario, String branch,
            String product)
    {
        this.keyword = step.getKeyword();
        this.step = step.getName();
        this.result = result.getStatus();
        this.reason = result.getErrorMessage();
        this.classname = match.getLocation();
        this.feature = feature.getName();
        this.scenario = scenario.getName();
        this.lineNumber = step.getLine();

        double durationSeconds = (double) result.getDuration() / 1000000000L;
        this.duration = Math.round(durationSeconds * 100.0) / 100.0;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.created = dateFormat.format(date);
        this.branch = branch;
        this.product = product;
    }
}
