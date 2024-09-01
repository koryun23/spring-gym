package org.example.service.impl;

import org.assertj.core.api.Assertions;
import org.example.dao.core.TraineeDao;
import org.example.dao.impl.TraineeDaoImpl;
import org.example.entity.Trainee;
import org.example.exception.TraineeNotFoundException;
import org.example.service.params.TraineeCreateParams;
import org.example.service.params.TraineeUpdateParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;


@ExtendWith(MockitoExtension.class)
class TraineeServiceImplTest {

    @InjectMocks
    private TraineeServiceImpl testSubject;

    @Spy
    private TraineeDao traineeDao;

    @BeforeEach
    public void init() {
        testSubject = new TraineeServiceImpl();
    }

    @Test
    public void testCreateWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.create(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testUpdateWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.update(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testDeleteWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.delete(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testSelectWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.select(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}