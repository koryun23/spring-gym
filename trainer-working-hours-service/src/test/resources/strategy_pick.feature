Feature: Strategy Pick

  Scenario: Action type is ADD
    Given Action type is ADD
    When Pick Strategy
    Then Should return instance of TrainerWorkingHoursAddStrategy

  Scenario: Action type is REMOVE
    Given Action type is REMOVE
    When Pick Strategy
    Then Should return instance of TrainerWorkingHoursRemoveStrategy

  Scenario: Action type is null
    Given Action type is null
    Then Should throw IllegalArgumentException