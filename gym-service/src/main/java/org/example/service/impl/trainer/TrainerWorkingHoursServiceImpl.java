package org.example.service.impl.trainer;


import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.TrainerWorkingHoursRequestDto;
import org.example.service.core.trainer.TrainerWorkingHoursService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TrainerWorkingHoursServiceImpl implements TrainerWorkingHoursService {

    private final WebClient webClient;

    public TrainerWorkingHoursServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public void sendData(TrainerWorkingHoursRequestDto requestDto) {

        log.info("Sending trainer data to the trainer working hours microservice");

        Mono<ResponseEntity> responseEntityMono = webClient
            .post()
            .uri("/trainer-working-hours")
            .body(Mono.just(requestDto), TrainerWorkingHoursRequestDto.class)
            .retrieve()
            .bodyToMono(ResponseEntity.class)
            .onErrorResume(e -> {
                System.out.println("Error: " + e);
                return Mono.empty();
            });

        responseEntityMono.subscribe(System.out::println);

        log.info("Successfully sent trainer data to the trainer working hours microservice");
    }
}
