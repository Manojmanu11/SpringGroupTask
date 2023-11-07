package com.example.Trading.entity;

import com.example.Trading.dto.OrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String stockSymbol;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    private double price;

    private int quantity;

    private LocalDateTime timestamp;
}
