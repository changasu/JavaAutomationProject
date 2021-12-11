Feature: Launch Google Results page
  Launch Google page
  Search text and click on the link

  Scenario Outline: TC_003_Launch Google Result
    Given User launches Google page
    Then User searches for a text <RowID> "SearchText"
    And User clicks on Google search button
    And User verifies the Google results
    And User clicks on the available result

    Examples: 
      | RowID |
      |     1 |

  Scenario Outline: TC_004_Verifies Google result
    Given User launches Google page
    Then User searches for a text <RowID> "SearchText"
    And User clicks on Google search button
    And User verifies the Google results

    Examples: 
      | RowID |
      |     1 |
