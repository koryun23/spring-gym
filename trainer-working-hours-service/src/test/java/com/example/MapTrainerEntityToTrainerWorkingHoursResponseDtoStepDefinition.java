package com.example;

import com.example.dto.TrainerWorkingHoursResponseDto;
import com.example.entity.TrainerEntity;
import com.example.mapper.TrainerMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

public class MapTrainerEntityToTrainerWorkingHoursResponseDtoStepDefinition extends
    TrainerWorkingHoursServiceSpringIntegrationTest {

    private TrainerEntity trainerEntity;
    private TrainerWorkingHoursResponseDto responseDto;

    @Autowired
    private TrainerMapper trainerMapper;

    @Given("TrainerEntity is null")
    public void whenTrainerEntityIsNull() {
        trainerEntity = null;
    }

    @Then("Should throw IllegalArgumentException saying that TrainerEntity must not be null")
    public void shouldThrowIllegalArgumentException() {
        Assertions.assertThatThrownBy(() -> trainerMapper.mapTrainerEntityToTrainerWorkingHoursResponseDto(trainerEntity))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("TrainerEntity must not be null");
    }

    @Given("Valid TrainerEntity")
    public void whenTrainerEntityIsValid() {
        trainerEntity = new TrainerEntity("username", "first", "last", true, 2024, 10, 1000L);
    }

    @When("Try to Map TrainerEntity to TrainerWorkingHoursResponseDto")
    public void tryToMapTrainerEntityToTrainerWorkingHoursResponseDto() {
        responseDto = trainerMapper.mapTrainerEntityToTrainerWorkingHoursResponseDto(trainerEntity);
    }

    @Then("Should return valid TrainerWorkingHoursResponseDto")
    public void shouldReturnValidTrainerWorkingHoursResponseDto() {
        Assertions.assertThat(responseDto).isEqualTo(new TrainerWorkingHoursResponseDto("username", 1000L));
    }
}
