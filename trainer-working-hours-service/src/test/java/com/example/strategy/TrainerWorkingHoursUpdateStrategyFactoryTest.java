package com.example.strategy;

import com.example.dto.ActionType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrainerWorkingHoursUpdateStrategyFactoryTest {

    private TrainerWorkingHoursUpdateStrategyFactory testSubject;

    @Mock
    private TrainerWorkingHoursAddStrategy addStrategy;

    @Mock
    private TrainerWorkingHoursRemoveStrategy removeStrategy;

    @BeforeEach
    public void init() {
        testSubject = new TrainerWorkingHoursUpdateStrategyFactory(addStrategy, removeStrategy);
    }

    @Test
    public void testGetStrategyWhenActionTypeIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.getStrategy(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetStrategyWhenActionTypeIsAdd() {
        Assertions.assertThat(testSubject.getStrategy(ActionType.ADD))
            .isExactlyInstanceOf(TrainerWorkingHoursAddStrategy.class);
    }

    @Test
    public void testGetStrategyWhenActionTypeIsDelete() {
        Assertions.assertThat(testSubject.getStrategy(ActionType.DELETE))
            .isExactlyInstanceOf(TrainerWorkingHoursRemoveStrategy.class);
    }

}