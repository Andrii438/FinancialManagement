package com.example.financemanagement.controller;

import com.example.financemanagement.entity.FinancialGoal;
import com.example.financemanagement.exception.FinancialGoalNotFoundException;
import com.example.financemanagement.service.FinancialGoalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/financial-goal/")
public class FinancialGoalController {
    private final FinancialGoalService financialGoalService;

    public FinancialGoalController(FinancialGoalService financialGoalService) {
        this.financialGoalService = financialGoalService;
    }

    @GetMapping
    public ResponseEntity<List<FinancialGoal>> getAllFinancialGoals(){
        List<FinancialGoal> financialGoals = financialGoalService.findAllFinancialGoals();
        if (financialGoals.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(financialGoals);
    }

    @GetMapping("{id}")
    public ResponseEntity<FinancialGoal> getFinancialGoalById(@PathVariable Long id){
        try {
            FinancialGoal goal = financialGoalService.findFinancialGoalById(id);
            return ResponseEntity.ok(goal);
        } catch (FinancialGoalNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
