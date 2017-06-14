package com.github.slyatbest.cukes_framework.hooks;

import java.io.IOException;
import java.net.URL;

import com.google.common.io.Resources;

import cucumber.api.Scenario;
import cucumber.api.java.After;

public class Hooks
{
    @After()
    public void embedImage(Scenario scenario)
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
