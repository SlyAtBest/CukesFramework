package com.github.slyatbest.cukes_framework.elasticsearch;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import cucumber.api.Result;
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
    private final NiceAppendable out;
    private final Properties properties;

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
        this.properties = loadBuildProperties();
    }

    /**
     * Method to retrieve the build.properties data.  If no file is found or if an error occurs whilst loading the file
     * then throw an {@link IllegalStateException}
     * @return Properties object containing the branch and product
     */
    private Properties loadBuildProperties()
    {
        Properties properties = new Properties();

        String userDirectory = System.getProperty("user.dir");

        if (userDirectory == null)
        {
            throw new IllegalStateException("Failed to find user directory");
        }

        Path parent = Paths.get(userDirectory).getParent();

        if (parent == null)
        {
            throw new IllegalStateException("Failed to get parent of user directory");
        }

        Path pathToProperties = Paths.get(parent.toString(), "/build.properties");
        try (InputStream input = new FileInputStream(pathToProperties.toString()))
        {
            properties.load(input);
        }
        catch (IOException e)
        {
            throw new IllegalStateException(String.format(
                    "Error whilst loading/reading \"build.properties\" file using path \"%s\"", pathToProperties));
        }

        return properties;
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
     * Method called after each step has been executed, which appends results data to the json output in ElasticSearch's bulk format
     * @param event {@link TestStepFinished}
     */
    private void handleTestStepFinished(TestStepFinished event)
    {
        appendResultToJsonOutput(event.testStep, event.result);
    }

    /**
     * Method that handles generating output in the Elasticsearch bulk format
     * @param testStep {@link TestStep}
     * @param result {@link Result}
     */
    private void appendResultToJsonOutput(TestStep testStep, Result result)
    {
        elasticStepResult = new ElasticStepResult(testStep, result, testCase, properties);
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