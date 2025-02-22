Feature: Strategy Pick

  Scenario: Action type is ADD
    Given Action type is ADD
    When Pick Strategy
    Then Should return instance of TrainerWorkingHoursAddStrategy