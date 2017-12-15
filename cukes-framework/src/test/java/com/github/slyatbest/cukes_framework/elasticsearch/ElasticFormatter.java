package com.github.slyatbest.cukes_framework.elasticsearch;

import java.util.ArrayList;
import java.util.List;

import gherkin.deps.com.google.gson.Gson;
import gherkin.deps.com.google.gson.GsonBuilder;
import gherkin.formatter.Formatter;
import gherkin.formatter.NiceAppendable;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;

public class ElasticFormatter implements Reporter, Formatter
{
    private static final int STARTING_BEFORE_HOOK_VALUE = -10000;
    private static final int STARTING_AFTER_HOOK_VALUE = 10000;

    private final NiceAppendable out;
    private ElasticStepResult elasticStepResult;
    private List<Step> steps = new ArrayList<Step>();
    private List<Result> results = new ArrayList<Result>();
    private List<Match> matches = new ArrayList<Match>();
    private Feature feature;
    private int beforeHook = STARTING_BEFORE_HOOK_VALUE;
    private int afterHook = STARTING_AFTER_HOOK_VALUE;

    public ElasticFormatter(Appendable out)
    {
        this.out = new NiceAppendable(out);
    }

    public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line)
    {
        // Noop
    }

    public void uri(String uri)
    {
        // Noop
    }

    public void feature(Feature feature)
    {
        this.feature = feature;
    }

    public void scenarioOutline(ScenarioOutline scenarioOutline)
    {
        // Noop
    }

    public void examples(Examples examples)
    {
        // Noop
    }

    public void startOfScenarioLifeCycle(Scenario scenario)
    {
        // Noop
    }

    public void background(Background background)
    {
        // Noop
    }

    public void scenario(Scenario scenario)
    {
        // Noop
    }

    public void step(Step step)
    {
        // Check if the step is an example step (can't use instanceof because it's private)
        // if it is scan the steps list and remove the duplicate step
        if (step.getClass().toString().equals("class cucumber.runtime.model.ExampleStep"))
        {
            for (int i = 0; i < steps.size(); i++)
            {
                if (steps.get(i).getLine() == step.getLine())
                {
                    steps.remove(i);
                }
            }
        }

        steps.add(step);
    }

    public void endOfScenarioLifeCycle(Scenario scenario)
    {
        // Check that all lists are the same size, if they are not something has gone horribly wrong
        if (steps.size() != results.size() || steps.size() != matches.size())
        {
            throw new RuntimeException("Expected number of steps, results and matches all to be equal");
        }

        // For each step in the steps list create a new step result using the values from the steps, results and matches list and 
        // include the current feature and scenario.  Then append the appropriate ElasticOperation and the ElasticStepResult to 
        // the output file.
        for (int i = 0; i < steps.size(); i++)
        {
            elasticStepResult = new ElasticStepResult(steps.get(i), results.get(i), matches.get(i), feature, scenario);
            out.append(gson().toJson(new ElasticOperation("results", "result"))).append("\n");
            out.append(gson().toJson(elasticStepResult)).append("\n");
        }

        // Reset lists and values
        steps.clear();
        results.clear();
        matches.clear();
        beforeHook = STARTING_BEFORE_HOOK_VALUE;
        afterHook = STARTING_AFTER_HOOK_VALUE;
    }

    public void done()
    {
        // Noop
    }

    public void close()
    {
        out.close();
    }

    public void eof()
    {
        // Noop
    }

    public void before(Match match, Result result)
    {
        steps.add(new Step(null, "Before", match.getLocation(), beforeHook++, null, null));
        results.add(result);
        matches.add(match);
    }

    public void result(Result result)
    {
        results.add(result);
    }

    public void after(Match match, Result result)
    {
        steps.add(new Step(null, "After", match.getLocation(), afterHook++, null, null));
        results.add(result);
        matches.add(match);
    }

    public void match(Match match)
    {
        matches.add(match);
    }

    public void embedding(String mimeType, byte[] data)
    {
        // Noop
    }

    public void write(String text)
    {
        // Noop
    }

    protected Gson gson()
    {
        return new GsonBuilder().create();
    }

}