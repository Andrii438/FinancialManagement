package com.example.financemanagement.repository;

import com.example.financemanagement.entity.User;
import com.example.financemanagement.entity.builder.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
@Sql(scripts = "/scripts/test_data.sql")
class UserRepositoryTest {
    public static final LocalDate LOCAL_DATE = LocalDate.of(1997, 12, 22);
    public static final String FIRST_NAME = "Andrii";
    public static final String SURNAME = "Mykhalchuk";
    public static final String USERNAME = "amykh";
    public static final long ID = 4L;

    @Autowired
    private UserRepository repository;

    private User user;

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
    public void shouldFindAllUsers(){
        List<User> users = repository.findAll();

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(3);
    }

    @Test
    public void shouldSaveUser(){
        List<User> usersBefore = repository.findAll();

        repository.save(user);

        List<User> usersAfter = repository.findAll();

        assertThat(usersBefore.size()).isLessThan(usersAfter.size());
        assertTrue(usersAfter.contains(user));

        User savedUser = usersAfter.stream()
                .filter(u -> u.getUsername().equals(USERNAME))
                .findFirst()
                .orElse(null);

        assertNotNull(savedUser);
        assertEquals(user, savedUser);
        assertNotNull(savedUser.getId());
    }

    @Test
    public void shouldFindById(){
        user.setId(ID);
        repository.save(user);
        Optional<User> foundUser = repository.findById(ID);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.get()).isEqualTo(user);
    }

    @Test
    public void shouldDeleteUser(){
        repository.save(user);
        Long userId = user.getId();
        List<User> usersBefore = repository.findAll();

        repository.deleteById(userId);
        List<User> usersAfter = repository.findAll();

        assertThat(usersBefore.size()).isGreaterThan(usersAfter.size());
        assertFalse(usersAfter.contains(user));
    }
}