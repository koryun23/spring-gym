package org.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.sql.Date;
import java.util.Collections;
import org.assertj.core.api.Assertions;
import org.example.controller.TraineeController;
import org.example.controller.UserController;
import org.example.dto.RestResponse;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeDeletionByUsernameRequestDto;
import org.example.dto.request.TraineeRetrievalByUsernameRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.entity.trainee.TraineeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class TraineeControllerStepDefinition {

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

    @Given("Valid TraineeCreationRequestDto")
    public void validTraineeCreationRequestDto() {
        traineeCreationRequestDto = new TraineeCreationRequestDto(
            "first", "last", Date.valueOf("2024-10-10"), "address"
        );
    }

    @When("Try to register a trainee")
    public void tryToRegisterATrainee() {
        responseEntity = traineeController.register(traineeCreationRequestDto);
        TraineeCreationResponseDto responseDto = (TraineeCreationResponseDto) responseEntity.getBody().getPayload();
        loggedInUsername = responseDto.getUsername();
        loggedInPassword = responseDto.getPassword();
    }

    @Then("Status code should be {int}")
    public void statusCodeShouldBe(int status) {
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(status));
    }

    @Given("Invalid TraineeCreationRequestDto")
    public void invalidTraineeCreationRequestDto() {
    }

    @Given("Valid TraineeRetrievalRequestDto")
    public void validTraineeRetrievalRequestDto() {
        traineeRetrievalRequestDto = new TraineeRetrievalByUsernameRequestDto(loggedInUsername);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(new User(loggedInUsername, loggedInPassword, Collections.emptyList()), loggedInPassword));
    }

    @When("Try to retrieve a trainee")
    public void tryToRetrieveATrainee() {
        responseEntity = traineeController.retrieve(loggedInUsername);
    }

    @Given("Invalid TraineeRetrievalRequestDto")
    public void invalidTraineeRetrievalRequestDto() {
    }

    @Given("Valid TraineeUpdateRequestDto")
    public void validTraineeUpdateRequestDto() {
    }

    @When("Try to update a trainee")
    public void tryToUpdateATrainee() {
    }

    @Given("Invalid TraineeUpdateRequestDto")
    public void invalidTraineeUpdateRequestDto() {
    }

    @Given("Valid TraineeDeleteRequestDto")
    public void validTraineeDeleteRequestDto() {
    }

    @When("Try to delete a trainee")
    public void tryToDeleteATrainee() {
    }

    @Given("Valid TraineeSwitchActivationStateRequestDto")
    public void validTraineeSwitchActivationStateRequestDto() {
    }

    @When("Try to switch activation state of a Trainee")
    public void tryToSwitchActivationStateOfATrainee() {
    }
}
