package com.example;

import com.example.dto.ActionType;
import com.example.entity.TrainerEntity;
import com.example.service.TrainerService;
import com.example.strategy.TrainerWorkingHoursUpdateStrategy;
import com.example.strategy.TrainerWorkingHoursUpdateStrategyFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

public class UpdateWorkingHoursStepDefinitions extends TrainerWorkingHoursServiceSpringIntegrationTest {

    private Long duration;
    private TrainerEntity trainerEntity;
    private TrainerWorkingHoursUpdateStrategy trainerWorkingHoursUpdateStrategy;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private TrainerWorkingHoursUpdateStrategyFactory strategyFactory;

    @Given("Valid TrainerEntity\\(current working hours for the given trainer - {long}, the value added - {long}) and ADD Strategy")
    public void givenTrainerEntityDoesNotExistStrategyAdd(Long currentDuration, Long addedDuration) {
        trainerEntity = new TrainerEntity("username", "first", "last", true, 2025, 10, addedDuration);
        trainerWorkingHoursUpdateStrategy = strategyFactory.getStrategy(ActionType.ADD);
    }

    @Given("Valid TrainerEntity\\(current working hours for the given trainer - {long}, the decrease - {long}) and REMOVE Strategy")
    public void givenTrainerEntityExistsStrategyRemove(Long currentDuration, Long removedDuration) {
        trainerEntity = new TrainerEntity("username", "first", "last", true, 2025, 10, removedDuration);
        trainerWorkingHoursUpdateStrategy = strategyFactory.getStrategy(ActionType.DELETE);
    }

    @When("Try to update working hours")
    public void tryToUpdateWorkingHours() {
        duration = trainerService.updateWorkingHours(trainerEntity, trainerWorkingHoursUpdateStrategy);
    }

    @Then("Should return 2000")
    public void shouldReturn2000() {
        Assertions.assertThat(duration).isEqualTo(2000L);
        Optional<TrainerEntity> optionalTrainerEntity =
            trainerService.findByUsernameAndMonthAndYear("username", 10, 2025);
        Assertions.assertThat(optionalTrainerEntity).isPresent();
        Assertions.assertThat(optionalTrainerEntity.get().getDuration()).isEqualTo(2000L);
    }

    @Then("Should return 0")
    public void shouldReturn1000() {
        Assertions.assertThat(duration).isEqualTo(0L);
        Optional<TrainerEntity> optionalTrainerEntity =
            trainerService.findByUsernameAndMonthAndYear("username", 10, 2025);
        Assertions.assertThat(optionalTrainerEntity).isPresent();
        Assertions.assertThat(optionalTrainerEntity.get().getDuration()).isEqualTo(0L);
    }

    @Then("Should return 3000")
    public void shouldReturn3000() {
        Assertions.assertThat(duration).isEqualTo(3000L);
        Optional<TrainerEntity> optionalTrainerEntity =
            trainerService.findByUsernameAndMonthAndYear("username", 10, 2025);
        Assertions.assertThat(optionalTrainerEntity).isPresent();
        Assertions.assertThat(optionalTrainerEntity.get().getDuration()).isEqualTo(3000L);
    }
}
