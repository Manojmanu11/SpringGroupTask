package com.example.Trading.service;

import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.TradeDto;
import com.example.Trading.dto.UpdatedPriceDto;
import com.example.Trading.entity.Order;

import java.util.List;
public interface StockOrderSystemService {

 Order addOrder(OrderDto orderDto);
         UpdatedPriceDto updatePrice(OrderDto orderDto);
         OrderDto matchOrders(OrderDto orderDto);

         OrderDto getPortfolio(OrderDto orderDto);

         TradeDto getTradeHistory(TradeDto tradeDto);
         }