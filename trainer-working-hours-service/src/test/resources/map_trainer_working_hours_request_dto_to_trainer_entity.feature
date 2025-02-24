Feature: Map TrainerWorkingHoursRequestDto to TrainerEntity

  Scenario: Map Trainer Working Hours Request Dto to Trainer Entity when Request Dto is null
    Given TrainerWorkingHoursRequestDto is null
    Then Should throw IllegalArgumentException saying that TrainerWorkingHoursRequestDto must not be null

  Scenario: Map Trainer Working Hours Request Dto to Trainer Entity when Request Dto is valid
    Given TrainerWorkingHoursRequestDto is valid
    When Try to Map TrainerWorkingHoursRequestDto to TrainerEntity
    Then Should return valid TrainerEntity