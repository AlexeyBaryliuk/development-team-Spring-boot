Feature: developers feature

  Scenario: the user gets all developers
    Given the following developers
      |developerId|firstName|lastName   |
      |1          |John     |Conor      |
      |2          | Vasily  |Golovachev |
    When the user request all the developers
    Then all the developers are returned