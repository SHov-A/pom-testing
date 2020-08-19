@all
Feature: Checking functionality combo boxes

  Background: User navigates to url
    Given User open browser
    Then User in homepage

  @firstTestCase
  Scenario: Check that all prices in result are smaller than selected price
    When User selecting price
    Then All prices in result are smaller than selected price

  @secondTestCase
  Scenario: Filter By Brand. Check that actual items count matches with the number in green box
    When User selecting brand of car
    Then Actual items count matches with the number in green box


