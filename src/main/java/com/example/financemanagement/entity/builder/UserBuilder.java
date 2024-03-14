package com.example.financemanagement.entity.builder;

import com.example.financemanagement.entity.FinancialGoal;
import com.example.financemanagement.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserBuilder {
    private Long id;
    private String firstName;
    private String surname;
    private LocalDate birthdate;
    private String username;
    private List<FinancialGoal> financialGoals = new ArrayList<>();


    public UserBuilder() {
        //Empty constructor
    }

    public UserBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder surname(String surname) {
        this.surname = surname;
        return this;
    }

    public UserBuilder birthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder financialGoals(List<FinancialGoal> financialGoals) {
        this.financialGoals = financialGoals;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setSurname(surname);
        user.setBirthdate(birthdate);
        user.setUsername(username);
        user.setFinancialGoals(financialGoals);
        return user;
    }
}
