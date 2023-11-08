package com.example.Trading.dto;

import lombok.Data;

@Data
public class UpdatePriceRequest {
    private String symbolName;
    private double price;
}
