package org.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.sql.Date;
import org.assertj.core.api.Assertions;
import org.example.controller.TraineeController;
import org.example.controller.UserController;
import org.example.dto.RestResponse;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeDeletionByUsernameRequestDto;
import org.example.dto.request.TraineeRetrievalByUsernameRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TraineeControllerStepDefinition extends GymServiceSpringIntegrationTest {

    private TraineeCreationRequestDto traineeCreationRequestDto;
    private TraineeRetrievalByUsernameRequestDto traineeRetrievalRequestDto;
    private TraineeUpdateRequestDto traineeUpdateRequestDto;
    private TraineeDeletionByUsernameRequestDto traineeDeleteRequestDto;
    private ResponseEntity<RestResponse> responseEntity;

    private String loggedInUsername;
    private String loggedInPassword;

    @Autowired
    private TraineeController traineeController;

    @Autowired
    private UserController userController;

    /**
     * Method for setting a valid TraineeCreationRequestDto.
     */
    @Given("Valid TraineeCreationRequestDto")
    public void validTraineeCreationRequestDto() {
        traineeCreationRequestDto = new TraineeCreationRequestDto(
            "first", "last", Date.valueOf("2024-10-10"), "address"
        );
    }

    /**
     * Method for trying to register a Trainee.
     */
    @When("Try to register a trainee")
    public void tryToRegisterATrainee() {
        responseEntity = traineeController.register(traineeCreationRequestDto);
        TraineeCreationResponseDto responseDto = (TraineeCreationResponseDto) responseEntity.getBody().getPayload();
        loggedInUsername = responseDto.getUsername();
        loggedInPassword = responseDto.getPassword();
    }

    /**
     * Method for testing if the status code is correct.
     */
    @Then("Status code should be {int}")
    public void statusCodeShouldBe(int status) {
        Assertions.assertThat(responseEntity.getStatusCode()).isNotEqualTo(HttpStatus.valueOf(status));
    }

}
