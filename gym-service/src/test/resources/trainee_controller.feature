Feature: Trainee Controller

  Scenario: Register Trainee with valid TraineeCreationRequestDto
    Given Valid TraineeCreationRequestDto
    When Try to register a trainee
    Then Status code should be 200


