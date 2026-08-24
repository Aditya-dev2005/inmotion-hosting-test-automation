# ============================================================
# Feature: InMotion Hosting - Home Page
# Covers: page load, navigation, UI elements
# ============================================================

@UI @HomePage
Feature: Home Page Verification

  Background:
    Given the user opens the InMotion Hosting home page

  @Smoke
  Scenario: Verify home page title
    Then the page title should contain "InMotion Hosting"

  @Smoke
  Scenario: Verify the InMotion logo is visible
    Then the InMotion logo should be displayed on the page

  @Regression
  Scenario: Verify the hero heading is present
    Then the hero section should display a main heading

  @Regression
  Scenario: Navigate to hosting plans via the navigation menu
    When the user clicks on the Hosting menu
    Then the URL should contain "hosting"

  @Regression
  Scenario: Verify footer is present on the home page
    Then the footer section should be visible

  @Regression
  Scenario: Navigate to Privacy Policy from footer
    When the user clicks the Privacy link in the footer
    Then the URL should contain "privacy"
