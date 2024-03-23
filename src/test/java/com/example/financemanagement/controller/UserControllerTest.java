package com.example.financemanagement.controller;

import com.example.financemanagement.entity.User;
import com.example.financemanagement.entity.builder.UserBuilder;
import com.example.financemanagement.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    public static final String FIND_BY_ID = "/v1/user/{id}";
    public static final String FIND_ALL = "/v1/user/";
    public static final String CREATE = "/v1/user/create";
    public static final Long GOAL_ID = 1L;
    public static final long ID = 10L;

    public static final LocalDate LOCAL_DATE = LocalDate.of(1997, 12, 22);
    public static final String FIRST_NAME = "Andrii";
    public static final String SURNAME = "Mykhalchuk";
    public static final String USERNAME = "amykh";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final User user = new UserBuilder()
            .birthdate(LOCAL_DATE)
            .firstName(FIRST_NAME)
            .surname(SURNAME)
            .username(USERNAME)
            .build();

    @Test
    public void testGetAllUsers_Success() throws Exception {

        List<User> mockGoals = Collections.singletonList(user);

        when(userService.findAllUsers()).thenReturn(mockGoals);

        MvcResult mvcResult = mockMvc.perform(get(FIND_ALL))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();

        assertThat(responseBody)
                .contains(FIRST_NAME)
                .contains(SURNAME)
                .contains(USERNAME)
                .contains(LOCAL_DATE.toString());
    }

    @Test
    public void testGetUsers_NoResult() throws Exception {
        when(userService.findAllUsers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get(FIND_ALL))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetUserById_Success() throws Exception {

        when(userService.findUserById(ID)).thenReturn(user);

        MvcResult mvcResult = mockMvc.perform(get(FIND_BY_ID, ID))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();

        assertThat(responseBody)
                .contains(FIRST_NAME)
                .contains(SURNAME)
                .contains(USERNAME)
                .contains(LOCAL_DATE.toString());
    }

    @Test
    public void testCreateUser() throws Exception {
        user.setId(ID);
        when(userService.createOrUpdateUser(user)).thenReturn(user);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json = mapper.writeValueAsString(user);

        MvcResult mvcResult = mockMvc.perform(post(CREATE, user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(mvcResult.getResponse().getHeader("Location"))
                .contains("http://localhost:8081/v1/user/10");

        verify(userService).createOrUpdateUser(user);
    }

    @Test
    public void testDeleteMethodById() throws Exception {
        mockMvc.perform(delete(FIND_BY_ID, ID))
                .andExpect(status().isNoContent());

        verify(userService).deleteUserById(ID);
    }

    @Test
    public void testDeleteMethodByGoal() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json = mapper.writeValueAsString(user);

        mockMvc.perform(delete(FIND_ALL, user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNoContent());

        verify(userService).deleteUser(user);
    }
}