Feature: Search Google page
  Launch Google page
  Search text and verify the result

  @Test1
  Scenario Outline: TC_001_Verifies the Google result
    Given User launches Google page
    Then User searches for a text <RowID> "SearchText"
    And User clicks on Google search button
    And User verifies the Google results

    Examples: 
      | RowID |
      |     1 |
      |     2 |

  @Test2
  Scenario: TC_002_Search Google Page
    Given User launches Google page
    Then User searches for a text "SearchText"
    And User clicks on Google search button