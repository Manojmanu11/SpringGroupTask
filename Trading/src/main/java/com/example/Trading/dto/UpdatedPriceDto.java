package com.example.Trading.dto;

import lombok.Data;

@Data
public class UpdatedPriceDto {
    private String infoMsg;
    private String stockSymbol;
    private double price;

}

