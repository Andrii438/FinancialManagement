package com.example.financemanagement.service;

import com.example.financemanagement.entity.FinancialGoal;

import java.util.List;

public interface FinancialGoalService {
    void deleteFinancialGoal(FinancialGoal financialGoal);
    void deleteFinancialGoalById(Long financialGoalId);
    FinancialGoal createOrUpdateFinancialGoal(FinancialGoal financialGoalToCreate);
    List<FinancialGoal> findAllFinancialGoals();
    FinancialGoal findFinancialGoalById(Long financialGoalId);
}
