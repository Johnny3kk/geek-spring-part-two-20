@brand
Feature: Brand

  Scenario Outline: Successful Login to the brands page, moving to the creation brand form and adding a new one
    Given I reopen web browser
    When I navigate to brands page
    And I login, providing username as "<username>" and password as "<password>"
    And I click on login
    When I click create new button
    And i enter brand "<name>"
    And i click submit button
    Then New brand with "<name>" should be in list showing on the brands page
    Given Close web browser again

    Examples:
      | username | password | name |
      | admin | 100 | Maxon |

