package com.example;

import com.example.dto.TrainerDto;
import com.example.entity.TrainerEntity;
import com.example.mapper.TrainerMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

public class MapTrainerEntityToTrainerDtoStepDefinition extends TrainerWorkingHoursServiceSpringIntegrationTest {

    private TrainerEntity trainerEntity;
    private TrainerDto trainerDto;

    @Autowired
    private TrainerMapper trainerMapper;

//    @Given("TrainerEntity is null")
//    public void whenTrainerEntityIsNull() {
//        trainerEntity = null;
//    }

//    @Then("Should throw IllegalArgumentException saying that TrainerEntity must not be null")
//    public void shouldThrowIllegalArgumentException() {
//        Assertions.assertThatThrownBy(() -> trainerMapper.mapTrainerEntityToTrainerDto(trainerEntity))
//            .isExactlyInstanceOf(IllegalArgumentException.class)
//            .hasMessage("TrainerEntity must not be null");
//    }

    @Given("TrainerEntity is valid")
    public void whenTrainerEntityIsValid() {
        trainerEntity = new TrainerEntity("username", "first", "last", true, 2024, 10, 1000L);
    }

    @When("Try to Map TrainerEntity to TrainerDto")
    public void tryToMapTrainerEntityToTrainerDto() {
        trainerDto = trainerMapper.mapTrainerEntityToTrainerDto(trainerEntity);
    }

    @Then("Should return valid TrainerDto")
    public void shouldReturnValidTrainerDto() {
        Assertions.assertThat(trainerDto).isEqualTo(new TrainerDto("username", "first", "last", true, 2024, 10, 1000L));
    }

}
