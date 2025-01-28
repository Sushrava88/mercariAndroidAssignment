@mercari
Feature: Purchase Flow

  Scenario: Verify user can successfully complete a purchase
    Given the user logs in with valid credentials
    When the user adds the first and second items to the cart
    And the user proceeds to checkout and enters address details
    Then the user should see the checkout overview and complete the purchase successfully