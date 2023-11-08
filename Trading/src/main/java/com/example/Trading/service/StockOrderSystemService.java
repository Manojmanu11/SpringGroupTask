package com.example.Trading.service;

import com.example.Trading.dto.*;
import com.example.Trading.entity.Order;

import java.util.List;


public interface StockOrderSystemService {

    Order addOrder(OrderDto orderDto);

    UpdatedPriceDto updatePrice(UpdatePriceRequest req);
    void matchOrder();

    List<PortfolioDto> getPortfolio();

    List<TradeDto> getTradeHistory();


}
