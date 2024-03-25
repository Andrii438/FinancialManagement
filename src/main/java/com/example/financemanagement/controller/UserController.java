package com.example.financemanagement.controller;

import com.example.financemanagement.entity.User;
import com.example.financemanagement.exception.UserNotFoundException;
import com.example.financemanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @PostMapping("create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createOrUpdateUser(user);

        return ResponseEntity.created(URI.create("http://localhost:8081/v1/user/" + savedUser.getId()))
                .build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFinancialGoal(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFinancialGoal(@RequestBody User user){
        userService.deleteUser(user);
        return ResponseEntity.noContent().build();
    }
}
