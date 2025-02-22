package com.example;

import com.example.dto.ActionType;
import com.example.strategy.TrainerWorkingHoursUpdateStrategy;
import com.example.strategy.TrainerWorkingHoursUpdateStrategyFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

public class StepDefinitions {

    private ActionType actionType;
    private TrainerWorkingHoursUpdateStrategy strategy;

    @Autowired
    private TrainerWorkingHoursUpdateStrategyFactory strategyFactory;

    @Given("Action type is ADD")
    public void actionTypeIsADD() {
        actionType = ActionType.ADD;
    }

    @When("Pick Strategy")
    public void pickStrategy() {
        if (actionType == ActionType.ADD) {
            strategy = strategyFactory.getStrategy(ActionType.ADD);
        }
    }

    @Then("Should return instance of TrainerWorkingHoursAddStrategy")
    public void shouldReturnTrainerWorkingHoursAddStrategy() {
        Assertions.assertThat(strategy).isExactlyInstanceOf(TrainerWorkingHoursUpdateStrategy.class);
    }
}
