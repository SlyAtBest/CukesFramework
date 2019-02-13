Feature: Test scenario intergration when there same step has two different statuses
  Here is a description of the feature
  
  Background: This background should pass
    Given I have a product

  Scenario: The first step should fail instead of being skipped
    Given this first step passes
    Then the color of each step should be updated
    And the exectution history for the test should be attached to the Jira story