package com.example.Trading.service;

import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.TradeDto;
import com.example.Trading.dto.UpdatedPriceDto;

public interface StockOrderSystemService {

    OrderDto addOrder(OrderDto orderDto);
    UpdatedPriceDto updatePrice(OrderDto orderDto);
    OrderDto matchOrders(OrderDto orderDto);

import com.example.Trading.entity.Order;
import com.example.Trading.entity.Trade;

import java.util.List;

import java.util.List;

public interface StockOrderSystemService {

    Order addOrder(OrderDto orderDto);

    UpdatedPriceDto updatePrice(OrderDto req);

    void matchOrder();


    OrderDto getPortfolio(OrderDto orderDto);

    List<TradeDto> getTradeHistory();


}
