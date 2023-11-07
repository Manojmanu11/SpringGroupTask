package com.example.Trading.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PortfolioDto {
    private String stockSymbol;
    private int quantity;
    private String orderStatus;
    private LocalDateTime timestamp;
}
