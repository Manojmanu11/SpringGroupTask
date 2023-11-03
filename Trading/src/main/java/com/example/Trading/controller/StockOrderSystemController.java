package com.example.Trading.controller;

import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.TradeDto;
import com.example.Trading.entity.Order;
import com.example.Trading.service.StockOrderSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class StockOrderSystemController {

    @Autowired
    StockOrderSystemService stockOrderService;

    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@RequestBody OrderDto orderDto) {
        Order order = stockOrderService.addOrder(orderDto);
        return new ResponseEntity<>(order,HttpStatus.CREATED);
    }
    @GetMapping("/history")
    public List<TradeDto> getTradeHistory() {
        return stockOrderService.getTradeHistory();
    }
}
