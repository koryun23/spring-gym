package com.example;

import com.example.dto.ActionType;
import com.example.dto.TrainerWorkingHoursRequestDto;
import com.example.entity.TrainerEntity;
import com.example.mapper.TrainerMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.sql.Date;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

public class MapTrainerWorkingHoursRequestDtoToTrainerEntityStepDefinition extends SpringIntegrationTest {

    private TrainerEntity trainerEntity;
    private TrainerWorkingHoursRequestDto requestDto;

    @Autowired
    private TrainerMapper trainerMapper;

    @Given("TrainerWorkingHoursRequestDto is null")
    public void whenTrainerWorkingHoursRequestDtoIsNull() {
        requestDto = null;
    }

    @Then("Should throw IllegalArgumentException saying that TrainerWorkingHoursRequestDto must not be null")
    public void shouldThrowIllegalArgumentException() {
        Assertions.assertThatThrownBy(() -> trainerMapper.mapTrainerWorkingHoursRequestDtoToTrainerEntity(requestDto))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("TrainerWorkingHoursRequestDto must not be null");
    }

    @Given("TrainerWorkingHoursRequestDto is valid")
    public void whenTrainerWorkingHoursRequestDtoIsValid() {
        requestDto = new TrainerWorkingHoursRequestDto(1L, "username", "first", "last", true, Date.valueOf("2024-10-10"), 1000L, ActionType.ADD);
    }

    @When("Try to Map TrainerWorkingHoursRequestDto to TrainerEntity")
    public void tryToMapTrainerWorkingHoursRequestDtoToTrainer() {
        trainerEntity = trainerMapper.mapTrainerWorkingHoursRequestDtoToTrainerEntity(requestDto);
    }

    @Then("Should return valid TrainerEntity")
    public void shouldReturnValidTrainerEntity() {
        Assertions.assertThat(trainerEntity).isEqualTo(new TrainerEntity("username", "first", "last", true, 2024, 10, 1000L));
    }
}
