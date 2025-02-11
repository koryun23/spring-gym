package org.example.messaging;

import com.example.dto.TrainerDto;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageListenerImpl {

    private static final String TRAINER_WORKING_HOURS_READ_QUEUE = "trainer.working.hours.read.queue";

    private CompletableFuture<List<TrainerDto>> completableFuture;

    @JmsListener(destination = TRAINER_WORKING_HOURS_READ_QUEUE, containerFactory = "jmsListenerContainerFactory")
    public List<TrainerDto> onReceiveTrainerWorkingHours(List<TrainerDto> trainerDtoList) {
        log.info("Received the trainer working hours - {}", trainerDtoList);
        completableFuture = new CompletableFuture<>();
        completableFuture.complete(trainerDtoList);
        return trainerDtoList;
    }

    public CompletableFuture<List<TrainerDto>> getCompletableFuture() {
        return completableFuture;
    }
}
