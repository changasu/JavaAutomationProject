Feature: Launch Google Result page
  Launch Google page
  Search text and click on the link

Scenario: TC_003_Launch Google Result
  Given User launches Google page
  Then User searches for a text "SearchText"
  And User clicks on Google search button
  And User verifies the Google results
  And User clicks on the available result
  
Scenario: TC_004_Verifies Google result
  Given User launches Google page
  Then User searches for a text "SearchText"
  And User clicks on Google search button
  And User verifies the Google results