package com.example.Trading.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class OrderDto {
    private String stockSymbol;
    private OrderType orderType;
    private double price;
    private int quantity;
    private String status;
    private LocalDateTime timestamp;
}
