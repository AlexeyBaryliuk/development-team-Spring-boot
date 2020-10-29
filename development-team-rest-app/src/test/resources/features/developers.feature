Feature: developers feature

  Scenario: the user gets all developers
    Given the following developers
      |developerId|firstName|lastName   |
      |1          |John     |Conor      |
      |2          | Vasily  |Golovachev |
    When the user request all the developers
    Then all the developers are returned

  Scenario: the user updates developers
    Given the following developer with name John and lastname Connor
    When the developer was updated with lastname NewConnor
    Then updated developer is back

  Scenario: the user deletes developers
    Given developer with name John and lastname Connor is present
    When the developer with developerId 1 deleted
    Then the following developer with developerId 1 isn't present

  Scenario: the user find developer by Id
    Given developer with name John and lastname Connor is present
    When the user tries to find developer by developerId 1
    Then the following developer with developerId 1 is returned

