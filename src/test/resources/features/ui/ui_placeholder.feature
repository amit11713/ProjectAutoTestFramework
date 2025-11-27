@UiTests
Feature: UI Window Handling Automation

  @UiTests01
  Scenario: Handle multiple windows 01
    Given I am on the window practice page with title "How to handle multiple windows using selenium"
    When I click on the home button
    Then a new tab should open
    When I switch to the newly opened tab
    Then I print the title of the page
    When I close the parent window
    Then the child window should remain
    When I close the child window
    Then all windows should be closed
  
  @UiTests02
  Scenario: Handle multiple windows 02
    Given I am on the window practice page with title "How to handle multiple windows using selenium"
    When I click on the Multiple windows button
    Then multiple windows should open
    When I print all the window titles
    Then I close all windows
    Then all windows should be closed