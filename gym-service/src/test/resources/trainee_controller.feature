Feature: Trainee Controller

  Scenario: Register Trainee with valid TraineeCreationRequestDto
    Given Valid TraineeCreationRequestDto
    When Try to register a trainee
    Then Status code should be 200

#  Scenario: Register Trainee with invalid TraineeCreationRequestDto
#    Given Invalid TraineeCreationRequestDto
#    When Try to register a trainee
#    Then Status code should be 406

  Scenario: Retrieve Trainee with valid TraineeRetrievalRequestDto
    Given Valid TraineeRetrievalRequestDto
    When Try to retrieve a trainee
    Then Status code should be 200

#  Scenario: Retrieve Trainee with invalid TraineeRetrievalRequestDto
#    Given Invalid TraineeRetrievalRequestDto
#    When Try to retrieve a trainee
#    Then Status code should be 406

#  Scenario: Update Trainee with valid TraineeUpdateRequestDto
#    Given Valid TraineeUpdateRequestDto
#    When Try to update a trainee
#    Then Status code should be 200

#  Scenario: Update Trainee with invalid TraineeUpdateRequestDto
#    Given Invalid TraineeUpdateRequestDto
#    When Try to update a trainee
#    Then Status code should be 406

#  Scenario: Delete Trainee with valid TraineeDeleteRequestDto
#    Given Valid TraineeDeleteRequestDto
#    When Try to delete a trainee
#    Then Status code should be 200

#  Scenario: Switch activation state of a Trainee with a valid TraineeSwitchActivationStateRequestDto
#    Given Valid TraineeSwitchActivationStateRequestDto
#    When Try to switch activation state of a Trainee
#    Then Status code should be 200