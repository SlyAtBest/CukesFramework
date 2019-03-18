Feature: Test Feature
  
  This is a simple test feature
  
  Background: Ensure we have a product
  
  This background has been added to specfically test what happens when two backgrounds with the same name exist in separate feature files.  Also
  to test what happens when a background has a description and multiple steps.
  
  Given I have a product
  And I complete another background step
  And I complete one further background step

  Scenario: I have a product and I want it to do something useful
    Given I have a product
    When I ask it to do something
    Then something useful happens
