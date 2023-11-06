package com.example.Trading.controller;

import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.UpdatedPrice;
import com.example.Trading.dto.UpdatedPriceDto;
import com.example.Trading.entity.Order;
import com.example.Trading.service.StockOrderSystemService;
import com.example.Trading.service.StockOrderSystemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
