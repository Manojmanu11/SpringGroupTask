package com.example.Trading.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

public class TradeDto {

    private String stockSymbol;

    private OrderType orderType;

    private double price;

    private int quantity;

    private LocalDateTime timestamp;
}
