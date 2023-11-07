package com.example.Trading.controller;

import com.example.Trading.dto.OrderDto;

import com.example.Trading.dto.UpdatedPriceDto;

import com.example.Trading.service.StockOrderSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

public class StockOrderSystemController {

    @Autowired
    StockOrderSystemService stockOrderService;

    @PutMapping("/updatePrice")
    public ResponseEntity<UpdatedPriceDto> stockPrice(@RequestBody OrderDto orderDto) {
        UpdatedPriceDto updatedOrder = stockOrderService.updatePrice(orderDto);

        if (updatedOrder != null) {
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
