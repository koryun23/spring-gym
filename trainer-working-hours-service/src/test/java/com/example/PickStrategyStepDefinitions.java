package com.example;

import com.example.dto.ActionType;
import com.example.strategy.TrainerWorkingHoursAddStrategy;
import com.example.strategy.TrainerWorkingHoursRemoveStrategy;
import com.example.strategy.TrainerWorkingHoursUpdateStrategy;
import com.example.strategy.TrainerWorkingHoursUpdateStrategyFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

public class PickStrategyStepDefinitions extends TrainerWorkingHoursServiceSpringIntegrationTest {

    private ActionType actionType;
    private TrainerWorkingHoursUpdateStrategy strategy;

    @Autowired
    private TrainerWorkingHoursUpdateStrategyFactory strategyFactory;

    @Given("Action type is ADD")
    public void actionTypeIsADD() {
        actionType = ActionType.ADD;
    }

    @Given("Action type is REMOVE")
    public void actionTypeIsREMOVE() {
        actionType = ActionType.DELETE;
    }

    @Given("Action type is null")
    public void actionTypeIsNull() {
        actionType = null;
    }

    @When("Pick Strategy")
    public void pickStrategy() {
        strategy = strategyFactory.getStrategy(actionType);
    }

    @Then("Should return instance of TrainerWorkingHoursAddStrategy")
    public void shouldReturnTrainerWorkingHoursAddStrategy() {
        Assertions.assertThat(strategy).isExactlyInstanceOf(TrainerWorkingHoursAddStrategy.class);
    }

    @Then("Should return instance of TrainerWorkingHoursRemoveStrategy")
    public void shouldReturnTrainerWorkingHoursRemoveStrategy() {
        Assertions.assertThat(strategy).isExactlyInstanceOf(TrainerWorkingHoursRemoveStrategy.class);
    }

    @Then("Should throw IllegalArgumentException")
    public void shouldThrowIllegalArgumentException() {
        Assertions.assertThatThrownBy(() -> strategyFactory.getStrategy(actionType))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
