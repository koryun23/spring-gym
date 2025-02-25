Feature: Map TrainerEntity to TrainerDto

  Scenario: Map TrainerEntity to TrainerDto when TrainerEntity is null
    Given TrainerEntity is null
    Then Should throw IllegalArgumentException saying that TrainerEntity must not be null

  Scenario: Map TrainerEntity to TrainerDto when TrainerEntity is valid
    Given TrainerEntity is valid
    When Try to Map TrainerEntity to TrainerDto
    Then Should return valid TrainerDto