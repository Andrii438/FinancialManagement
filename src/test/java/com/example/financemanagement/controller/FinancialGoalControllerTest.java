package com.example.financemanagement.controller;

import com.example.financemanagement.entity.FinancialGoal;
import com.example.financemanagement.entity.builder.FinancialGoalBuilder;
import com.example.financemanagement.service.FinancialGoalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FinancialGoalController.class)
class FinancialGoalControllerTest {

    public static final String FIND_BY_ID = "/v1/financial-goal/{id}";
    public static final String FIND_ALL = "/v1/financial-goal/";
    public static final String CREATE = "/v1/financial-goal/create";
    public static final String GOAL_NAME = "Children";
    public static final String AMOUNT = "1000";
    public static final String ACHIEVED_DATE = "2030-12-31";
    public static final Long GOAL_ID = 1L;
    public static final long ID = 10L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinancialGoalService financialGoalService;

    private final FinancialGoal financialGoal = new FinancialGoalBuilder()
            .goalName("Children")
            .amount(BigDecimal.valueOf(1000L))
            .achievedDate(LocalDate.of(2030, 12, 31))
            .build();

    @Test
    void testGetAllFinancialGoals_Success() throws Exception {

        List<FinancialGoal> mockGoals = Collections.singletonList(financialGoal);

        when(financialGoalService.findAllFinancialGoals()).thenReturn(mockGoals);

        MvcResult mvcResult = mockMvc.perform(get(FIND_ALL))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();

        assertThat(responseBody)
                .contains(GOAL_NAME)
                .contains(AMOUNT)
                .contains(ACHIEVED_DATE);
    }

    @Test
    void testGetFinancialGoals_NoResult() throws Exception {
        when(financialGoalService.findAllFinancialGoals()).thenReturn(Collections.emptyList());

        mockMvc.perform(get(FIND_ALL))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetFinancialGoalById_Success() throws Exception {

        when(financialGoalService.findFinancialGoalById(GOAL_ID)).thenReturn(financialGoal);

        MvcResult mvcResult = mockMvc.perform(get(FIND_BY_ID, GOAL_ID))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();

        assertThat(responseBody)
                .contains(GOAL_NAME)
                .contains(AMOUNT)
                .contains(ACHIEVED_DATE);
    }

    @Test
    void testCreateFinancialGoal() throws Exception {
        financialGoal.setId(ID);
        when(financialGoalService.createOrUpdateFinancialGoal(financialGoal)).thenReturn(financialGoal);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json = mapper.writeValueAsString(financialGoal);

        MvcResult mvcResult = mockMvc.perform(post(CREATE, financialGoal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(mvcResult.getResponse().getHeader("Location"))
                .contains("http://localhost:8081/v1/financial-goal/10");

        verify(financialGoalService).createOrUpdateFinancialGoal(financialGoal);
    }

    @Test
    void testDeleteMethodById() throws Exception {
        mockMvc.perform(delete(FIND_BY_ID, ID))
                .andExpect(status().isNoContent());

        verify(financialGoalService).deleteFinancialGoalById(ID);
    }

    @Test
    void testDeleteMethodByGoal() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json = mapper.writeValueAsString(financialGoal);

        mockMvc.perform(delete(FIND_ALL, financialGoal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNoContent());

        verify(financialGoalService).deleteFinancialGoal(financialGoal);
    }
}