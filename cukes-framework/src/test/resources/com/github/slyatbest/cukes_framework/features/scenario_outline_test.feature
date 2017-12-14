Feature: Scenario Outline Test

  Background: Ensure we have a product
    Given I have a product

  Scenario Outline: I have a product and I want to test it using the <Value> example of a scenario outline
    Given I test the <Value> value
    Then the results should be visible in the results

    Examples: A simple set of values
      | Value  |
      | first  |
      | second |
      | third  |
