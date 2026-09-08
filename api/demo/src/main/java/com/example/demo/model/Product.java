package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    private String image;
    private BigDecimal rating = BigDecimal.ZERO;
    private Boolean available = true;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}