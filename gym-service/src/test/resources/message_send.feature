Feature: Message Send

  Scenario: Send a TrainerWorkingHoursRequestDto to the specified message queue
    Given Valid TrainerWorkingHoursRequestDto
    When Try to TrainerWorkingHoursRequestDto to the specified message queue
    Then Message should be in the queue
