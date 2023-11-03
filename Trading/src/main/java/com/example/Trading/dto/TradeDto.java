package com.example.Trading.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TradeDto {

    private String stockSymbol;

    private OrderType orderType;

    private double price;

    private int quantity;

    private LocalDateTime timestamp;
}
