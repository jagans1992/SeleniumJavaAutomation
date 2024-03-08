#Author: Jagan

Feature: Title of your feature
  I want to use this template for my feature file


  @regression
  Scenario Outline: Title of your scenario outline
    Given User launch a web page "<URL>"
    Then User get the page title and match with <title>
    Then User verify the content "Content"
    And User verify the huge data on the page using filename "<Disclosure>" and using filepath "HugeText"

    Examples: 
      | URL  | title | Disclosure  |
      | /usc/lpaca/citi/cards/simplicity/ps/index.html | Citi Simplicity® Card | :SimplicityPS: |
