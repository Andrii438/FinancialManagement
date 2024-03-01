package com.example.financemanagement.entity.builder;

import com.example.financemanagement.entity.FinancialGoal;
import com.example.financemanagement.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FinancialGoalBuilder {
    private Long id;
    private String goalName;
    private LocalDate achievedDate;
    private BigDecimal amount;
    private User user;

    public FinancialGoalBuilder() {
    }

    public FinancialGoalBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public FinancialGoalBuilder goalName(String goalName) {
        this.goalName = goalName;
        return this;
    }

    public FinancialGoalBuilder achievedDate(LocalDate achievedDate) {
        this.achievedDate = achievedDate;
        return this;
    }

    public FinancialGoalBuilder amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public FinancialGoalBuilder user(User user) {
        this.user = user;
        return this;
    }

    public FinancialGoal build() {
        FinancialGoal financialGoal = new FinancialGoal();
        financialGoal.setId(id);
        financialGoal.setGoalName(goalName);
        financialGoal.setAchievedDate(achievedDate);
        financialGoal.setAmount(amount);
        financialGoal.setUser(user);
        return financialGoal;
    }
}
