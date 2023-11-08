package com.example.Trading.dto;

import com.example.Trading.exceptionhandler.OrderTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
public class OrderDto {
    private String stockSymbol;
    @JsonDeserialize(using = OrderTypeDeserializer.class)
    private OrderType orderType;
    private double price;
    private int quantity;
}
