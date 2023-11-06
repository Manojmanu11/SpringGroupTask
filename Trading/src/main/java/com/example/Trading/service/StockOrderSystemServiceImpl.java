package com.example.Trading.service;

import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.TradeDto;
import com.example.Trading.entity.Order;
import com.example.Trading.entity.Trade;
import com.example.Trading.exception.InvalidOrderTypeException;
import com.example.Trading.repository.OrderRepository;
import com.example.Trading.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockOrderSystemServiceImpl implements StockOrderSystemService{
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    TradeRepository tradeRepository;

    @Override
    public Order addOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setStockSymbol(orderDto.getStockSymbol());
        order.setOrderType(orderDto.getOrderType());
        order.setQuantity(orderDto.getQuantity());
        order.setPrice(orderDto.getPrice());
        order.setStatus(orderDto.getStatus());
        order.setTimestamp(LocalDateTime.now());

        return orderRepository.save(order);
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
    public List<TradeDto> getTradeHistory() {
        List<Trade> trades = tradeRepository.findAll();
        List<TradeDto> tradeHistory = trades.stream()
                .map(this::mapToTradeHistoryDTO)
                .collect(Collectors.toList());
        return  tradeHistory;
    }

        private TradeDto mapToTradeHistoryDTO(Trade trade) {
            TradeDto tradeHistoryDTO = new TradeDto();
//            tradeHistoryDTO.setId(trade.getId());
            tradeHistoryDTO.setStockSymbol(trade.getStockSymbol());
            tradeHistoryDTO.setOrderType(trade.getOrderType());
            tradeHistoryDTO.setPrice(trade.getPrice());
            tradeHistoryDTO.setQuantity(trade.getQuantity());
            tradeHistoryDTO.setTimestamp(trade.getTimestamp());
            return tradeHistoryDTO;
        }

}