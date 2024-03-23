package com.example.financemanagement.repository;

import com.example.financemanagement.entity.FinancialGoal;
import com.example.financemanagement.entity.builder.FinancialGoalBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
@Sql(scripts = "/scripts/test_data.sql")
class FinancialGoalRepositoryTest {
    public static final BigDecimal AMOUNT = BigDecimal.valueOf(1000000);
    public static final LocalDate LOCAL_DATE = LocalDate.of(2030, 12, 31);
    public static final String GOAL_NAME = "Retirement";

    @Autowired
    private FinancialGoalRepository financialGoalRepository;

    private FinancialGoal financialGoal;

    @BeforeEach
    void setUp() {
        financialGoal = new FinancialGoalBuilder()
                .goalName(GOAL_NAME)
                .achievedDate(LOCAL_DATE)
                .amount(AMOUNT)
                .build();
    }

    @Test
    void shouldSaveTheGivenGoal() {
        int sizeBefore = financialGoalRepository.findAll().size();

        FinancialGoal saved = financialGoalRepository.saveAndFlush(financialGoal);

        int sizeAfter = financialGoalRepository.findAll().size();
        assertAll("saved",
                () -> assertThat(saved).isNotNull(),
                () -> assertThat(saved.getAmount()).isEqualTo(AMOUNT),
                () -> assertThat(saved.getAchievedDate()).isEqualTo(LOCAL_DATE),
                () -> assertThat(saved.getGoalName()).isEqualTo(GOAL_NAME)
        );
        assertThat(sizeAfter).isGreaterThan(sizeBefore);
    }

    @Test
    void shouldFindAllGoals() {
        int size = financialGoalRepository.findAll().size();

        assertThat(size).isEqualTo(3);
    }

    @Test
    void shouldDeleteTheGivenGoal() {
        FinancialGoal saved = financialGoalRepository.save(financialGoal);
        int sizeBefore = financialGoalRepository.findAll().size();

        financialGoalRepository.delete(saved);
        int sizeAfter = financialGoalRepository.findAll().size();

        assertThat(sizeAfter).isLessThan(sizeBefore);
    }

    @Test
    void shouldUpdateTheGivenGoal() {
        FinancialGoal saved = financialGoalRepository.saveAndFlush(financialGoal);

        BigDecimal updatedAmount = BigDecimal.valueOf(1500000);
        LocalDate updatedDate = LocalDate.of(2035, 6, 15);
        String updatedGoalName = "Extended Retirement";

        saved.setAmount(updatedAmount);
        saved.setAchievedDate(updatedDate);
        saved.setGoalName(updatedGoalName);

        FinancialGoal updated = financialGoalRepository.saveAndFlush(saved);

        FinancialGoal retrieved = financialGoalRepository.findById(updated.getId()).orElse(null);

        assertAll("updated",
                () -> assertThat(retrieved).isNotNull(),
                () -> assertThat(retrieved.getAmount()).isEqualTo(updatedAmount),
                () -> assertThat(retrieved.getAchievedDate()).isEqualTo(updatedDate),
                () -> assertThat(retrieved.getGoalName()).isEqualTo(updatedGoalName)
        );
    }
}