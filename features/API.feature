Feature: API Verification

  Scenario: User get and Verify Json API
    Given user hit the api and store json response in "testDataAPI"
    Then user verify the api json response in "testDataAPI"
    
    
  Scenario: User get and Verify XML API
    Given user hit the api and store XML response in "testDataAPI"
    Then user verify the api XML response in "testDataAPI"