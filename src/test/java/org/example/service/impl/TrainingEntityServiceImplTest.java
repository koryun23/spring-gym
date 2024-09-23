package org.example.service.impl;

import org.assertj.core.api.Assertions;
import org.example.dao.core.TrainingDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrainingEntityServiceImplTest {

    private TrainingServiceImpl testSubject;

    @Mock
    private TrainingDao trainingDao;

    @BeforeEach
    public void init() {
        testSubject = new TrainingServiceImpl();
    }

    @Test
    public void testCreateWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.create(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void selectWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.select(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByIdWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findById(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }


}