Feature: A Test Feature To Exercise Pulse
  This is a feature file to ensure that Pulse and Scenario integrate well with our existing tools

  Background: Ensure we have a product
    This background has been added to specifically test what happens when two backgrounds with the same name exist in separate feature files.  Also
    to test what happens when a background has a description and multiple steps.

    Given I have a product
    And I complete another background step
    And I complete one further background step
    
  Scenario: The First Scenario Testing Pulse Integration
    This scenario will test a few basics aspects of the integration between scenario, pulse and qTest Manager
    Given this step passes
    Then the color of this step should be updated
    
  Scenario: The Second Scenario Testing Pulse Integration
    This scenario will continue testing Pulse Integration
    Given I have installed the scenario plugin in Jira
    Then this step should also pass