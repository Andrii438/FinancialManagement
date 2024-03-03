package com.example.financemanagement.service;

import com.example.financemanagement.entity.User;

import java.util.List;

public interface UserService {
    public void deleteUser(User user);
    public void deleteUserById(Long userId);
    public User createOrUpdateUser(User userToCreate);
    public List<User> findAllUsers();
    public User findUserById(Long userId);
}
