package com.example.financemanagement.service;

import com.example.financemanagement.entity.User;
import com.example.financemanagement.entity.builder.UserBuilder;
import com.example.financemanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    public static final LocalDate LOCAL_DATE = LocalDate.of(1997, 12, 22);
    public static final String FIRST_NAME = "Andrii";
    public static final String SURNAME = "Mykhalchuk";
    public static final String USERNAME = "amykh";
    @BeforeEach
    void setUp() {
        user = new UserBuilder()
                .birthdate(LOCAL_DATE)
                .firstName(FIRST_NAME)
                .surname(SURNAME)
                .username(USERNAME)
                .build();
    }
    @Test
    void deleteUser() {
        userService.deleteUser(user);

        verify(userRepository).delete(user);
    }

    @Test
    void deleteUserById() {
    }

    @Test
    void createOrUpdateUser() {
    }

    @Test
    void findAllUsers() {
    }

    @Test
    void findUserById() {
    }
}