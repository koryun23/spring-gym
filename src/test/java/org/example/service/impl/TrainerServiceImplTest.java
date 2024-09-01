package org.example.service.impl;

import org.assertj.core.api.Assertions;
import org.example.dao.impl.TrainerDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TrainerServiceImplTest {

    @InjectMocks
    private TrainerServiceImpl testSubject;

    @Mock
    private TrainerDaoImpl trainerDao;

    @BeforeEach
    public void setUp() {
        testSubject = new TrainerServiceImpl();
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
    public void testSelectWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.select(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testSelectByUsernameWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.selectByUsername(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testSelectByUsernameWhenEmpty() {
        Assertions.assertThatThrownBy(() -> testSubject.selectByUsername(""))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByIdWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findById(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByUsernameWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findByUsername(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByUsernameWhenEmpty() {
        Assertions.assertThatThrownBy(() -> testSubject.findByUsername(""))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}