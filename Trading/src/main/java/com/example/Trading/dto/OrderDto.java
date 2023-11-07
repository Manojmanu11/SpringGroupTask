package com.example.Trading.dto;

import com.example.Trading.constants.ErrorConstants;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

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
