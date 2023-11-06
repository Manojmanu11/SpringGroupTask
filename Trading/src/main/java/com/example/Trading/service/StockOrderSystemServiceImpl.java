package com.example.Trading.service;

import com.example.Trading.constants.StringConstants;
import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.OrderType;
import com.example.Trading.dto.TradeDto;
import com.example.Trading.entity.Order;
import com.example.Trading.entity.Trade;
import com.example.Trading.repository.OrderRepository;
import com.example.Trading.repository.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class StockOrderSystemServiceImpl implements StockOrderSystemService {
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
    public void matchOrder() {

        log.info("Returned by StockOrderSystemService : matchOrder");
        List<Order> buyOrders = orderRepository.findBuyOrders();
        List<Order> sellOrders = orderRepository.findSellOrders();
        for (Order buyOrder : buyOrders) {
            for (Order sellOrder : sellOrders) {
                if (isMatchingOrders(buyOrder, sellOrder)) {
                    int transactionQuantity = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());
                    if (transactionQuantity > 0) {
                        Trade buyTrade = createTrade(buyOrder, StringConstants.Buy, transactionQuantity);
                        Trade sellTrade = createTrade(sellOrder, StringConstants.Sell, transactionQuantity);
                        updateOrder(buyOrder, transactionQuantity);
                        updateOrder(sellOrder, transactionQuantity);
//                        updateUserTradeHistory( buyTrade);
//                        updateUserTradeHistory( sellTrade);


                    }
                }
            }
        }
    }


    private boolean isMatchingOrders(Order buyOrder, Order sellOrder) {
        return buyOrder.getStockSymbol().equals(sellOrder.getStockSymbol()) && buyOrder.getPrice() >= sellOrder.getPrice() && buyOrder.getStatus().equals(StringConstants.PENDING) && sellOrder.getStatus().equals(StringConstants.PENDING);
    }

    private Trade createTrade(Order order, String orderType, int quantity) {
        Trade trade = new Trade();
        trade.setStockSymbol(order.getStockSymbol());
        trade.setOrderType(OrderType.valueOf(orderType));
        trade.setQuantity(quantity);
        trade.setPrice(order.getPrice());
        trade.setTimestamp(LocalDateTime.now());
        return trade;
    }

     void updateOrder(Order order, int quantity) {
        order.setQuantity(order.getQuantity() - quantity);
        if (order.getQuantity() == 0) {
            order.setStatus(StringConstants.EXECUTED);
        }
        orderRepository.save(order);
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
