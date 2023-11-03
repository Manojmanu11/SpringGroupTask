package com.example.Trading.controller;

import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.TradeDto;
import com.example.Trading.dto.UpdatedPrice;
import com.example.Trading.service.StockOrderSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockOrderSystemController {

    @Autowired
    StockOrderSystemService stockOrderSystemService;

    @PutMapping("/updatePrice")
    public ResponseEntity<UpdatedPrice> stockPrice(@RequestBody OrderDto orderDto) {
        UpdatedPrice updatedOrder = stockOrderSystemService.updatePrice(orderDto);

        if (updatedOrder != null) {
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
