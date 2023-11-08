package com.example.Trading.controller;

import com.example.Trading.constants.UrlConstants;
import com.example.Trading.dto.*;
import com.example.Trading.entity.Order;
import com.example.Trading.service.StockOrderSystemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UrlConstants.STOCKS)
public class StockOrderSystemController {

    @Autowired
    StockOrderSystemService stockOrderService;

    @Autowired
    StockOrderSystemService stockOrderSystemService;

    @PostMapping(UrlConstants.ADD)
    public ResponseEntity<Order> addOrder(@Valid @RequestBody OrderDto orderDto) {
        Order order = stockOrderService.addOrder(orderDto);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }
    @PutMapping(UrlConstants.UPDATE_PRICE)
    public ResponseEntity<UpdatedPriceDto> stockPrice(@RequestBody OrderDto orderDto) {
        UpdatedPriceDto updatedOrder = stockOrderService.updatePrice(orderDto);

        if (updatedOrder != null) {
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(UrlConstants.PORTFOLIO)
    public List<PortfolioDto> getPortfolio(){
        return stockOrderSystemService.getPortfolio();
    }

    @GetMapping(UrlConstants.TRADE_HISTORY)
    public List<TradeDto> getTradeHistory() {
        return stockOrderService.getTradeHistory();}
}
