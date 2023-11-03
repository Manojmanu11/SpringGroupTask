package com.example.Trading.service;

import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.TradeDto;
import com.example.Trading.entity.Order;

public interface StockOrderSystemService {

    OrderDto addOrder(OrderDto orderDto);
    OrderDto updatePrice( String StockSymbol,Double price );
    OrderDto matchOrders(OrderDto orderDto);

    OrderDto getPortfolio(OrderDto orderDto);

    TradeDto getTradeHistory(TradeDto tradeDto);



}
