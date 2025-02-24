Feature: Update Working Hours

  Scenario: Try to update working hours when TrainerEntity is valid and does not exist and TrainerWorkingHoursUpdateStrategy is ADD
    Given Valid TrainerEntity(current working hours for the given trainer - 0, the value added - 2000) and ADD Strategy
    When Try to update working hours
    Then Should return 2000

  Scenario: Try to update working hours when TrainerEntity is valid and TrainerWorkingHoursUpdateStrategy is ADD
    Given Valid TrainerEntity(current working hours for the given trainer - 2000, the value added - 1000) and ADD Strategy
    When Try to update working hours
    Then Should return 3000

  Scenario: Try to update working hours when TrainerEntity is valid and TrainerWorkingHoursUpdateStrategy is REMOVE
    Given Valid TrainerEntity(current working hours for the given trainer - 3000, the decrease - 3000) and REMOVE Strategy
    When Try to update working hours
    Then Should return 0

