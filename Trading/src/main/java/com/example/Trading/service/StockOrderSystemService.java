package com.example.Trading.service;

import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.TradeDto;
import com.example.Trading.entity.Order;
import com.example.Trading.entity.Trade;

import java.util.List;

public interface StockOrderSystemService {

    Order addOrder(OrderDto orderDto);
    OrderDto updatePrice( String StockSymbol,Double price );
   void matchOrder();


    OrderDto getPortfolio(OrderDto orderDto);

    TradeDto getTradeHistory(TradeDto tradeDto);



}
