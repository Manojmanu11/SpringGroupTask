package com.example.Trading.service;

import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.TradeDto;
import com.example.Trading.repository.OrderRepository;
import com.example.Trading.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockOrderSystemServiceImpl implements StockOrderSystemService{
     @Autowired
     OrderRepository orderRepository;
     @Autowired
     TradeRepository tradeRepository;

    @Override
    public OrderDto addOrder(OrderDto orderDto) {
        return null;
    }

    @Override
    public OrderDto updatePrice(String StockSymbol, Double price) {
        return null;
    }

    @Override
    public OrderDto matchOrders(OrderDto orderDto) {
        return null;
    }

    @Override
    public OrderDto getPortfolio(OrderDto orderDto) {
        return null;
    }

    @Override
    public TradeDto getTradeHistory(TradeDto tradeDto) {
        return null;
    }
}
