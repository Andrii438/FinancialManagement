package com.example.financemanagement.service;

import com.example.financemanagement.entity.FinancialGoal;
import com.example.financemanagement.entity.builder.FinancialGoalBuilder;
import com.example.financemanagement.exception.FinancialGoalNotFoundException;
import com.example.financemanagement.repository.FinancialGoalRepository;
import com.example.financemanagement.service.impl.FinancialGoalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinancialGoalServiceImplTest {

    public static final long ID = 5L;

    @Mock
    private FinancialGoalRepository financialGoalRepository;

    @InjectMocks
    private FinancialGoalServiceImpl financialGoalService;

    private FinancialGoal financialGoal;

    public static final BigDecimal AMOUNT = BigDecimal.valueOf(1000000);
    public static final LocalDate LOCAL_DATE = LocalDate.of(2030, 12, 31);
    public static final String GOAL_NAME = "Retirement";

    @BeforeEach
    void setUp() {
        financialGoal = new FinancialGoalBuilder()
                .id(ID)
                .goalName(GOAL_NAME)
                .achievedDate(LOCAL_DATE)
                .amount(AMOUNT)
                .build();
    }

    @Test
    void deleteFinancialGoal() {
        financialGoalService.deleteFinancialGoal(financialGoal);

        verify(financialGoalRepository).delete(financialGoal);
    }

    @Test
    void deleteFinancialGoalById() {
        financialGoalService.deleteFinancialGoalById(ID);

        verify(financialGoalRepository).deleteById(ID);
    }

    @Test
    void createOrUpdateFinancialGoal() {
        financialGoalService.createOrUpdateFinancialGoal(financialGoal);

        verify(financialGoalRepository).save(financialGoal);
    }

    @Test
    void findAllFinancialGoals() {
        when(financialGoalRepository.findAll()).thenReturn(Collections.singletonList(financialGoal));
        List<FinancialGoal> financialGoals = financialGoalService.findAllFinancialGoals();

        assertThat(financialGoals).hasSize(1);

        verify(financialGoalRepository).findAll();
    }

    @Test
    void findUserById() {
        when(financialGoalRepository.findById(ID)).thenReturn(Optional.of(financialGoal));
        FinancialGoal foundFinancialGoal = financialGoalService.findFinancialGoalById(ID);

        assertAll(
                () -> assertThat(foundFinancialGoal).isNotNull(),
                () -> assertThat(foundFinancialGoal.getGoalName()).isEqualTo(GOAL_NAME),
                () -> assertThat(foundFinancialGoal.getAmount()).isEqualTo(AMOUNT),
                () -> assertThat(foundFinancialGoal.getAchievedDate()).isEqualTo(LOCAL_DATE)
        );

        verify(financialGoalRepository).findById(ID);
    }

    @Test
    void findUserByIdThrowsAnException() {
        assertThrows(FinancialGoalNotFoundException.class, () -> financialGoalService.findFinancialGoalById(ID));
    }
}