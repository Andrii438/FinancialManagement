package com.example.financemanagement.exception;

public class FinancialGoalNotFoundException extends RuntimeException {
    public FinancialGoalNotFoundException(String message) {
        super(message);
    }

    public FinancialGoalNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
