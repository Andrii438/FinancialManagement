package com.example.financemanagement.controller;

import com.example.financemanagement.entity.FinancialGoal;
import com.example.financemanagement.entity.builder.FinancialGoalBuilder;
import com.example.financemanagement.service.FinancialGoalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FinancialGoalController.class)
class FinancialGoalControllerTest {

    public static final String FIND_BY_ID = "/v1/financial-goal/{id}";
    public static final String FIND_ALL = "/v1/financial-goal/";
    public static final String GOAL_NAME = "Children";
    public static final String AMOUNT = "1000";
    public static final String ACHIEVED_DATE = "2030-12-31";
    public static final Long GOAL_ID = 1L;

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
    public void testGetAllFinancialGoals_Success() throws Exception {

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
    public void testGetFinancialGoals_NoResult() throws Exception {
        when(financialGoalService.findAllFinancialGoals()).thenReturn(Collections.emptyList());

        mockMvc.perform(get(FIND_ALL))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetFinancialGoalById_Success() throws Exception {

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

}