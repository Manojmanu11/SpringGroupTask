package com.example.Trading.service;

import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.TradeDto;
import com.example.Trading.dto.UpdatedPriceDto;


public interface StockOrderSystemService {

    OrderDto addOrder(OrderDto orderDto);
    UpdatedPriceDto updatePrice(OrderDto orderDto);
    OrderDto matchOrders(OrderDto orderDto);

    OrderDto getPortfolio(OrderDto orderDto);

    TradeDto getTradeHistory(TradeDto tradeDto);



}
