package com.example.Trading.service;

import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.PortfolioDto;
import com.example.Trading.dto.TradeDto;
import com.example.Trading.entity.Order;

import java.util.List;

public interface StockOrderSystemService {

    OrderDto addOrder(OrderDto orderDto);
    OrderDto updatePrice( String StockSymbol,Double price );
    OrderDto matchOrders(OrderDto orderDto);

    public List<PortfolioDto> getPortfolio();

    TradeDto getTradeHistory(TradeDto tradeDto);



}
