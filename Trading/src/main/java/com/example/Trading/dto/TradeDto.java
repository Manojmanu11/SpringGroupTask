package com.example.Trading.dto;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TradeDto {

    private String stockSymbol;

    private OrderType orderType;

    private double price;

    private int quantity;

    private LocalDateTime timestamp;
}
