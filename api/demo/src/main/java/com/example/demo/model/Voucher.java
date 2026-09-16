package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "vouchers")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    private BigDecimal discount;
    private BigDecimal minOrder = BigDecimal.ZERO;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer quantity = 0;
    private Boolean status = true;
}