Feature: API REST Testing Automation

  Scenario: Retrieve user data via GET request
    Given I have the API endpoint for users
    When I send a GET request to retrieve user data
    Then I should receive a valid response with status 200