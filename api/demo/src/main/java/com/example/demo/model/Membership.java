package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class Membership {

    private Long userId;
    private String name;
    private String level;
    private BigDecimal totalCompletedSpend;
    private String nextLevel;
    private BigDecimal remainingAmount;
    private Integer progressPercent;
    private List<String> unlockedFeatures;
    private List<String> lockedFeatures;
}