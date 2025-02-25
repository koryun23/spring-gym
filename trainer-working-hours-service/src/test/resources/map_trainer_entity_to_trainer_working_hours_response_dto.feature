Feature: Map TrainerEntity to TrainerWorkingHoursResponseDto

  Scenario: Map TrainerEntity to TrainerWorkingHoursResponseDto when TrainerEntity is null
    Given TrainerEntity is null
    Then Should throw IllegalArgumentException saying that TrainerEntity must not be null

  Scenario: Map Trainer Entity to Trainer Working Hours Response Dto when TrainerEntity is valid
    Given Valid TrainerEntity
    When Try to Map TrainerEntity to TrainerWorkingHoursResponseDto
    Then Should return valid TrainerWorkingHoursResponseDto