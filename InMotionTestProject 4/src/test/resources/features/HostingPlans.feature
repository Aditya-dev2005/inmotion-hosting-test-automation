# ============================================================
# Feature: InMotion Hosting - Hosting Plans
# Covers: Shared, VPS, WordPress, Dedicated hosting pages
# ============================================================

@UI @HostingPlans
Feature: Hosting Plans Verification

  @Smoke
  Scenario: Verify Shared Hosting page loads
    Given the user navigates to the Shared Hosting page
    Then the page heading should contain "Hosting"
    And pricing plan cards should be visible

  @Smoke
  Scenario: Verify VPS Hosting page loads
    Given the user navigates to the VPS Hosting page
    Then the page heading should contain "Hosting"
    And pricing plan cards should be visible

  @Regression
  Scenario: Verify WordPress Hosting page loads
    Given the user navigates to the WordPress Hosting page
    Then the page heading should contain "Hosting"
    And pricing plan cards should be visible

  @Regression
  Scenario: Verify Dedicated Server page loads
    Given the user navigates to the Dedicated Hosting page
    Then the page heading should contain "Server"
    And pricing plan cards should be visible

  @Regression
  Scenario Outline: Verify plan prices are displayed for <planType>
    Given the user navigates to the <planType> Hosting page
    Then the plan prices should be visible on the page

    Examples:
      | planType   |
      | Shared     |
      | VPS        |
      | WordPress  |
