package com.example.financemanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "financial_goal")
@Table
@Data
@EqualsAndHashCode(exclude = "user")
public class FinancialGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "goal_name")
    private String goalName;

    @Column(name = "achieved_date")
    private LocalDate achievedDate;

    @Column(name = "amount")
    private BigDecimal amount;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
