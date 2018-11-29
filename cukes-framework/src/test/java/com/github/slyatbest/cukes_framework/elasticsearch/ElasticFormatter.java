package com.github.slyatbest.cukes_framework.elasticsearch;

import cucumber.api.Result;
import cucumber.api.Result.Type;
import cucumber.api.TestCase;
import cucumber.api.TestStep;
import cucumber.api.event.EventHandler;
import cucumber.api.event.EventPublisher;
import cucumber.api.event.TestCaseStarted;
import cucumber.api.event.TestRunFinished;
import cucumber.api.event.TestStepFinished;
import cucumber.api.formatter.Formatter;
import cucumber.api.formatter.NiceAppendable;
import gherkin.deps.com.google.gson.Gson;
import gherkin.deps.com.google.gson.GsonBuilder;

public class ElasticFormatter implements Formatter
{
    private final EventHandler<TestCaseStarted> caseStartedHandler = new EventHandler<TestCaseStarted>()
    {
        @Override
        public void receive(TestCaseStarted event)
        {
            handleTestCaseStarted(event);
        }
    };

    private final EventHandler<TestStepFinished> stepFinishedHandler = new EventHandler<TestStepFinished>()
    {
        @Override
        public void receive(TestStepFinished event)
        {
            handleTestStepFinished(event);
        }
    };

    private final EventHandler<TestRunFinished> runFinishedHandler = new EventHandler<TestRunFinished>()
    {
        @Override
        public void receive(TestRunFinished event)
        {
            finishReport();
        }
    };

    private final NiceAppendable out;

    private TestCase testCase;
    private ElasticStepResult elasticStepResult;

    /**
     * Constructor
     * 
     * @param out {@link Appendable}
     */
    public ElasticFormatter(Appendable out)
    {
        this.out = new NiceAppendable(out);
    }

    /**
     * Method the stores the {@link TestCase} at the point of the test case starting
     * @param event {@link TestCaseStarted}
     */
    private void handleTestCaseStarted(TestCaseStarted event)
    {
        this.testCase = event.testCase;
    }

    /**
     * Method called after each step has been executed, which appends results data to the json output in ElasticSearch's bulk format.
     * steps marked as 'SKIPPED' are explicitly ignored
     * @param event {@link TestStepFinished}
     */
    private void handleTestStepFinished(TestStepFinished event)
    {
        if (!event.result.getStatus().equals(Type.SKIPPED))
        {
            appendResultToJsonOutput(event.testStep, event.result);
        }
    }

    /**
     * Method that handles generating output in the Elasticsearch bulk format
     * @param testStep {@link TestStep}
     * @param result {@link Result}
     */
    private void appendResultToJsonOutput(TestStep testStep, Result result)
    {
        elasticStepResult = new ElasticStepResult(testStep, result, testCase);
        Gson gson = new GsonBuilder().create();
        out.append(gson.toJson(new ElasticOperation("email-web-results", "result"))).append("\n");
        out.append(gson.toJson(elasticStepResult)).append("\n");
    }

    /**
     * Method called at the end of all test cases, which closes the output json file
     */
    private void finishReport()
    {
        out.close();
    }

    @Override
    public void setEventPublisher(EventPublisher publisher)
    {
        publisher.registerHandlerFor(TestCaseStarted.class, caseStartedHandler);
        publisher.registerHandlerFor(TestStepFinished.class, stepFinishedHandler);
        publisher.registerHandlerFor(TestRunFinished.class, runFinishedHandler);

    }

}
