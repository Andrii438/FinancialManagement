package com.example.financemanagement.controller;

import com.example.financemanagement.entity.User;
import com.example.financemanagement.exception.UserNotFoundException;
import com.example.financemanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/user/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        try {
            User user = userService.findUserById(id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
//
//    @PostMapping("create")
//    public ResponseEntity<FinancialGoal> createFinancialGoal(@RequestBody FinancialGoal financialGoal) {
//        FinancialGoal savedGoal = financialGoalService.createOrUpdateFinancialGoal(financialGoal);
//
//        return ResponseEntity.created(URI.create("http://localhost:8081/v1/financial-goal/" + savedGoal.getId()))
//                .build();
//    }
//
//    @DeleteMapping("{id}")
//    public ResponseEntity<Void> deleteFinancialGoal(@PathVariable Long id){
//        financialGoalService.deleteFinancialGoalById(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping
//    public ResponseEntity<Void> deleteFinancialGoal(@RequestBody FinancialGoal financialGoal){
//        financialGoalService.deleteFinancialGoal(financialGoal);
//        return ResponseEntity.noContent().build();
//    }
}
