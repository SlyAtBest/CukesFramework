Feature: POC Test Scenario Integration With Pulse
  Here is a description of the feature
  
  Background: Install the plugin
    Given I have freshly installed the scenario plugin in Jira

  Scenario: Test Pulse Rules
    Given this first step passes
    Then the color of each step should be updated
    And the exectution history for the test should be attached to the Jira story