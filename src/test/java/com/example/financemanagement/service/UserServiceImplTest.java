package com.example.financemanagement.service;

import com.example.financemanagement.entity.User;
import com.example.financemanagement.entity.builder.UserBuilder;
import com.example.financemanagement.exception.UserNotFoundException;
import com.example.financemanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    public static final long ID = 5L;
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
                .id(ID)
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
        userService.deleteUserById(ID);

        verify(userRepository).deleteById(ID);
    }

    @Test
    void createOrUpdateUser() {
        userService.createOrUpdateUser(user);

        verify(userRepository).save(user);
    }

    @Test
    void findAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        List<User> users = userService.findAllUsers();

        assertThat(users).hasSize(1);

        verify(userRepository).findAll();
    }

    @Test
    void findUserById() {
        when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        User foundUser = userService.findUserById(ID);

        assertAll(
                () -> assertThat(foundUser).isNotNull(),
                () -> assertThat(foundUser.getUsername()).isEqualTo(USERNAME),
                () -> assertThat(foundUser.getBirthdate()).isEqualTo(LOCAL_DATE),
                () -> assertThat(foundUser.getFirstName()).isEqualTo(FIRST_NAME),
                () -> assertThat(foundUser.getSurname()).isEqualTo(SURNAME)
        );

        verify(userRepository).findById(ID);
    }

    @Test()
    void findUserByIdThrowsAnException() {
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(ID));
    }
}