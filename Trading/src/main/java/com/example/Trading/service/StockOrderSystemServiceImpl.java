package com.example.Trading.service;

import com.example.Trading.constants.StringConstants;
import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.PortfolioDto;
import com.example.Trading.dto.TradeDto;
import com.example.Trading.entity.Order;
import com.example.Trading.repository.OrderRepository;
import com.example.Trading.repository.TradeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
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


    public List<PortfolioDto> getPortfolio() {

        List<Order> orders = orderRepository.findAll();
        List<PortfolioDto> portfolioDTOs = orders.stream().map(order -> {
            PortfolioDto portfolioDTO = new PortfolioDto();
            portfolioDTO.setStockSymbol(order.getStockSymbol());
            portfolioDTO.setQuantity(order.getQuantity());
            portfolioDTO.setOrderStatus(order.getStatus());
            portfolioDTO.setTimestamp(order.getTimestamp());
            return portfolioDTO;
        }).collect(Collectors.toList());

        return portfolioDTOs;
    }




    @Override
    public TradeDto getTradeHistory(TradeDto tradeDto) {
        return null;
    }
}
