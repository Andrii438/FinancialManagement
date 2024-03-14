package com.example.financemanagement.service.impl;

import com.example.financemanagement.entity.FinancialGoal;
import com.example.financemanagement.exception.FinancialGoalNotFoundException;
import com.example.financemanagement.repository.FinancialGoalRepository;
import com.example.financemanagement.service.FinancialGoalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinancialGoalServiceImpl implements FinancialGoalService {

    private final FinancialGoalRepository repository;

    public FinancialGoalServiceImpl(FinancialGoalRepository repository) {
        this.repository = repository;
    }

    @Override
    public void deleteFinancialGoal(FinancialGoal financialGoal) {
        repository.delete(financialGoal);
    }

    @Override
    public void deleteFinancialGoalById(Long financialGoalId) {
        repository.deleteById(financialGoalId);
    }

    @Override
    public FinancialGoal createOrUpdateFinancialGoal(FinancialGoal financialGoalToCreate) {
        return repository.save(financialGoalToCreate);
    }

    @Override
    public List<FinancialGoal> findAllFinancialGoals() {
        return repository.findAll();
    }

    @Override
    public FinancialGoal findFinancialGoalById(Long financialGoalId) {
        return repository.findById(financialGoalId)
                .orElseThrow(() -> new FinancialGoalNotFoundException("Financial goal with " + financialGoalId + " not found"));
    }
}
