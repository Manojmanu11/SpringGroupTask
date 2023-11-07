package com.example.Trading.service;

import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.PortfolioDto;
import com.example.Trading.dto.TradeDto;
import com.example.Trading.dto.UpdatedPriceDto;
import com.example.Trading.entity.Order;

import java.util.List;


public interface StockOrderSystemService {

    Order addOrder(OrderDto orderDto);

    UpdatedPriceDto updatePrice(OrderDto req);

    void matchOrder();

    List<PortfolioDto> getPortfolio();

    List<TradeDto> getTradeHistory();


}
