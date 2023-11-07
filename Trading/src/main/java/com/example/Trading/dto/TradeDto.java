package com.example.Trading.dto;


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
