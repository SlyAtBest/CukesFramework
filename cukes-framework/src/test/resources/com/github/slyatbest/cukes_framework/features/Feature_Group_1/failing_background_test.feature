Feature: Failing Background Test

  Background: This background will fail
    Given my background step fails

  Scenario: Skipped scenario due to failing background
    Then this entire scenario should be skipped
