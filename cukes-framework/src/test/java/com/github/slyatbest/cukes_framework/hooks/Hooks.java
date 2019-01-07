package com.github.slyatbest.cukes_framework.hooks;

import java.io.IOException;
import java.net.URL;

import com.google.common.io.Resources;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks
{
    @Before()
    public void printStartingTest()
    {
        System.out.println("***Starting Test***");
    }

    @After()
    public void embedImage(Scenario scenario)
    {
        if (scenario.isFailed())
        {
            URL image = Hooks.class.getResource("/com/github/slyatbest/cukes_framework/images/image.png");
            {
                try
                {
                    if (image == null)
                    {
                        throw new IOException("Failed to load image");
                    }

                    byte[] data = Resources.toByteArray(image);
                    scenario.embed(data, "image/png");
                }
                catch (IOException e)
                {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
}
