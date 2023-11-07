package com.example.Trading.dto;

import lombok.Data;

@Data
public class UpdatedPrice {
    private String infoMsg;
    private String stockSymbol;
    private double price;
}