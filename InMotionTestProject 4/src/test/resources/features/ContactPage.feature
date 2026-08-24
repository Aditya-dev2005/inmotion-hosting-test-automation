# ============================================================
# Feature: InMotion Hosting - Contact Page
# NOTE: The live /contact page has no traditional name/email/
# message form. It offers phone, email, and a live chat button
# instead. Scenarios below reflect the real page.
# ============================================================

@UI @Contact
Feature: Contact Page Validation

  Background:
    Given the user opens the InMotion Hosting contact page

  @Smoke
  Scenario: Verify contact page loads successfully
    Then the contact page heading should contain "Contact"

  @Regression
  Scenario: Verify Chat with Sales option is available
    Then the Chat with Sales button should be visible

  @Regression
  Scenario: Verify Sales contact details are available
    Then a sales phone link should be visible
    And a sales email link should be visible
