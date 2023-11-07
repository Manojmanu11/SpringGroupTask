package com.example.Trading.service;

import com.example.Trading.constants.StringConstants;
import com.example.Trading.dto.*;
import com.example.Trading.entity.Order;
import com.example.Trading.entity.Trade;
import com.example.Trading.repository.OrderRepository;
import com.example.Trading.repository.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StockOrderSystemServiceImpl implements StockOrderSystemService {
    private final OrderRepository orderRepository;
    private final TradeRepository tradeRepository;
    int additionalValue = 0;

    @Autowired
    public StockOrderSystemServiceImpl(OrderRepository orderRepository, TradeRepository tradeRepository) {
        this.orderRepository = orderRepository;
        this.tradeRepository = tradeRepository;
    }

    @Override
    public Order addOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setStockSymbol(orderDto.getStockSymbol());
        order.setOrderType(orderDto.getOrderType());
        order.setQuantity(orderDto.getQuantity());
        order.setPrice(orderDto.getPrice());
        order.setStatus(StringConstants.PENDING);
        order.setTimestamp(LocalDateTime.now());

        return orderRepository.save(order);
    }

    public UpdatedPriceDto updatePrice(OrderDto req) {
        List<Order> stockOrders = orderRepository.findByStockSymbol(req.getStockSymbol());
        additionalValue = 1;

        if (!stockOrders.isEmpty()) {
            double newPrice = req.getPrice();
            OrderType orderTypeToMatch = req.getOrderType();

            boolean updated = false; // Flag to track if any orders were updated

            for (Order order : stockOrders) {
                OrderType orderType = order.getOrderType();
                if (orderType != null && orderType.equals(orderTypeToMatch)) {
                    // Only update orders with the specified order type
                    order.setPrice(newPrice);
                    orderRepository.save(order);
                    updated = true; // Mark that an order was updated
                }
            }

            if (updated) {
                UpdatedPriceDto priceDTO = new UpdatedPriceDto();
                priceDTO.setInfoMsg("Successfully Updated");
                priceDTO.setStockSymbol(req.getStockSymbol());
                priceDTO.setPrice(newPrice);
                return priceDTO;
            } else {
                // No matching orders were found
                UpdatedPriceDto priceDTO = new UpdatedPriceDto();
                priceDTO.setInfoMsg("No matching orders found pls check OrderType");
                return priceDTO;
            }
        }

        return null; // Return null if no orders were found for the stock symbol
    }


    @Scheduled(fixedRate = 60000) // Scheduled task to clear the updated price every minute
    public void clearUpdatedPrice() {
        log.info("The updated price has been cleared");
        additionalValue = 0;
    }


    @Scheduled(fixedRate = 30000)
    public void matchOrder() {
        log.info("Returned by StockOrderSystemService : matchOrder");
        if (additionalValue != 0) {
            System.out.println(additionalValue);
            log.info("Update price method is called");
            List<Order> buyOrders = orderRepository.findBuyOrders();
            List<Order> sellOrders = orderRepository.findSellOrders();
            for (Order buyOrder : buyOrders) {
                for (Order sellOrder : sellOrders) {
                    if (isMatchingOrders(buyOrder, sellOrder)) {
                        int transactionQuantity = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());
                        if (transactionQuantity > 0) {
                            updateOrder(buyOrder, transactionQuantity);
                            updateOrder(sellOrder, transactionQuantity);


                        }
                    }
                }
            }
        }
    }




    private boolean isMatchingOrders(Order buyOrder, Order sellOrder) {
        return buyOrder.getStockSymbol().equals(sellOrder.getStockSymbol()) && buyOrder.getPrice() >= sellOrder.getPrice() && buyOrder.getStatus().equals(StringConstants.PENDING) && sellOrder.getStatus().equals(StringConstants.PENDING);
    }


    void updateOrder(Order order, int quantity) {

        order.setQuantity(order.getQuantity() - quantity);
        if (order.getQuantity() == 0) {
            order.setStatus(StringConstants.EXECUTED);
        }
        orderRepository.save(order);
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

    public List<TradeDto> getTradeHistory() {
        List<Trade> trades = tradeRepository.findAll();
        List<TradeDto> tradeDtoHistory = trades.stream()
                .map(this::mapToTradeHistoryDTO)
                .collect(Collectors.toList());
        return tradeDtoHistory;
    }

    private TradeDto mapToTradeHistoryDTO(Trade trade) {
        TradeDto tradeHistoryDTO = new TradeDto();
        tradeHistoryDTO.setStockSymbol(trade.getStockSymbol());
        tradeHistoryDTO.setOrderType(trade.getOrderType());
        tradeHistoryDTO.setPrice(trade.getPrice());
        tradeHistoryDTO.setQuantity(trade.getQuantity());
        tradeHistoryDTO.setTimestamp(trade.getTimestamp());
        return tradeHistoryDTO;
    }
}
