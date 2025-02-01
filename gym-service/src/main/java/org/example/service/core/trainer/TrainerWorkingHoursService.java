package org.example.service.core.trainer;

import org.example.dto.request.TrainerWorkingHoursRequestDto;
import org.example.dto.response.TrainerWorkingHoursResponseDto;

public interface TrainerWorkingHoursService {

    void sendData(TrainerWorkingHoursRequestDto requestDto);
}
