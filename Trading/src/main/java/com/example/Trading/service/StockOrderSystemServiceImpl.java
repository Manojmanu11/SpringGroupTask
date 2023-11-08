package com.example.Trading.service;

import com.example.Trading.constants.StringConstants;
import com.example.Trading.dto.*;
import com.example.Trading.entity.Order;
import com.example.Trading.entity.Trade;
import com.example.Trading.repository.OrderRepository;
import com.example.Trading.repository.TradeRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service

@Transactional
public class StockOrderSystemServiceImpl implements StockOrderSystemService {
    private final OrderRepository orderRepository;
    private final TradeRepository tradeRepository;
    int additionalValue = 0; // value will be updated is updatePrice is executed
    private final Logger log = Logger.getLogger(getClass().getName());
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

    public UpdatedPriceDto updatePrice(UpdatePriceRequest req) {
        List<Order> stockOrders = orderRepository.findByStockSymbol(req.getSymbolName());
        additionalValue = 1;

        if (!stockOrders.isEmpty()) {
            for (Order order : stockOrders) {
                // Update the price of each matching order to the new price
                order.setPrice(req.getPrice());
                orderRepository.save(order);
            }

            UpdatedPriceDto priceDTO = new UpdatedPriceDto();
            priceDTO.setInfoMsg("Successfully Updated");
            priceDTO.setStockSymbol(req.getSymbolName());
            priceDTO.setPrice(req.getPrice());
            return priceDTO;
        } else {
            // No matching orders were found
            UpdatedPriceDto priceDTO = new UpdatedPriceDto();
            priceDTO.setInfoMsg("No matching orders found, please check stock symbol");
            return priceDTO;
        }
    }



    @Scheduled(fixedRate = 60000) // Scheduled task to clear the updated price every minute
    public void clearUpdatedPrice() {
        log.info("The updated price has been cleared");
        additionalValue = 0;
    }


    @Scheduled(fixedRate = 30000) // Scheduled task to execute the orders based on criteria
    public void matchOrder() {
        log.info("Returned by StockOrderSystemService : matchOrder");
        System.out.println(additionalValue);
        if (additionalValue != 0) { // checking if updatedPrice method is called.
            log.info("Update price method is called");
            List<Order> buyOrders = orderRepository.findBuyOrders();
            List<Order> sellOrders = orderRepository.findSellOrders();
            for (Order buyOrder : buyOrders) {
                for (Order sellOrder : sellOrders) {
                    if (isMatchingOrders(buyOrder, sellOrder)) {
                        log.info("Order excuted successfully :");
                        int transactionQuantity = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());
                        if (transactionQuantity > 0) {
                            updateOrder(buyOrder, transactionQuantity);//updating order.
                            updateOrder(sellOrder, transactionQuantity);

                            Trade buyTrade = createTrade(buyOrder, StringConstants.Buy, transactionQuantity);
                            Trade sellTrade = createTrade(sellOrder, StringConstants.Sell, transactionQuantity);


//                            updateTradeHistory( buyTrade);
//                            updateTradeHistory( sellTrade);
                        }

                    }
                }

            }

        }
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


    private boolean isMatchingOrders(Order buyOrder, Order sellOrder) { // matching oder based on condition for execution.
        return buyOrder.getStockSymbol().equals(sellOrder.getStockSymbol()) && buyOrder.getPrice() >= sellOrder.getPrice() && buyOrder.getStatus().equals(StringConstants.PENDING) && sellOrder.getStatus().equals(StringConstants.PENDING);
    }


    void updateOrder(Order order, int quantity) {
// updating order after transaction
        order.setQuantity(order.getQuantity() - quantity);
        if (order.getQuantity() == 0) {
            order.setStatus(StringConstants.EXECUTED);
        }
        orderRepository.save(order);
    }

    public List<PortfolioDto> getPortfolio() {

        List<Order> orders = orderRepository.findAll();
        List<PortfolioDto> portfolioDTOs = orders.stream().map(order -> {
            log.info("Returned by StockOrderSystemService : getPortfolio");
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
