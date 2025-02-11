package org.example.service.core.trainer;

import com.example.dto.TrainerWorkingHoursRequestDto;
import com.example.dto.TrainerWorkingHoursRetrievalRequestDto;

public interface TrainerWorkingHoursService {

    void updateWorkingHours(TrainerWorkingHoursRequestDto requestDto);

    void getWorkingHours(TrainerWorkingHoursRetrievalRequestDto requestDto);
}
