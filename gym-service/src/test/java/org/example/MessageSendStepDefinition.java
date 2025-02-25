package org.example;

import com.example.dto.ActionType;
import com.example.dto.TrainerWorkingHoursRequestDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.sql.Date;
import org.assertj.core.api.Assertions;
import org.example.service.core.trainer.TrainerWorkingHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;

public class MessageSendStepDefinition extends GymServiceSpringIntegrationTest {

    private TrainerWorkingHoursRequestDto requestDto;

    private final static String QUEUE = "trainer.working.hours.queue.local";

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private TrainerWorkingHoursService trainerWorkingHoursService;

    @Given("Valid TrainerWorkingHoursRequestDto")
    public void validTrainerWorkingHoursRequestDto() {
        requestDto =
            new TrainerWorkingHoursRequestDto(1L, "username", "first", "last", true, Date.valueOf("2024-10-10"), 1000L,
                ActionType.ADD);
    }

    @When("Try to TrainerWorkingHoursRequestDto to the specified message queue")
    public void tryToTrainerWorkingHoursRequestDtoToTheSpecifiedMessageQueue() {
        trainerWorkingHoursService.updateWorkingHours(requestDto);
    }

    @Then("Message should be in the queue")
    public void messageShouldBeInTheQueue() {
        TrainerWorkingHoursRequestDto sentMessage =
            (TrainerWorkingHoursRequestDto) jmsTemplate.receiveAndConvert(QUEUE);

        Assertions.assertThat(sentMessage).isEqualTo(requestDto);
    }
}
