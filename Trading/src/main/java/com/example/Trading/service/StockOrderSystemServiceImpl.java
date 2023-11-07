package com.example.Trading.service;

import com.example.Trading.constants.StringConstants;
import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.TradeDto;
import com.example.Trading.dto.UpdatedPriceDto;
import com.example.Trading.entity.Order;
import com.example.Trading.entity.Trade;

import com.example.Trading.exception.InvalidOrderTypeException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.Trading.repository.OrderRepository;
import com.example.Trading.repository.TradeRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Service
public class StockOrderSystemServiceImpl implements StockOrderSystemService{
    

    @Autowired
    OrderRepository orderRepository;
    

@Slf4j
public class StockOrderSystemServiceImpl implements StockOrderSystemService {
    private final OrderRepository orderRepository;
    private final TradeRepository tradeRepository;
    int additionalValue = 1;

    @Autowired
    public StockOrderSystemServiceImpl(OrderRepository orderRepository, TradeRepository tradeRepository) {
        this.orderRepository = orderRepository;
        this.tradeRepository = tradeRepository;
    }

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

    public UpdatedPriceDto updatePrice(OrderDto req) {
        List<Order> stockOrders = orderRepository.findByStockSymbol(req.getStockSymbol());

        if (!stockOrders.isEmpty()) {
            double newPrice = req.getPrice();

            for (Order order : stockOrders) {
                order.setPrice(newPrice);
                orderRepository.save(order);
            }

            UpdatedPriceDto priceDTO = new UpdatedPriceDto();
            priceDTO.setInfoMsg("Successfully Updated");
            priceDTO.setStockSymbol(req.getStockSymbol());
            priceDTO.setPrice(newPrice);
            return priceDTO;
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
            log.info("Updateprice method is called");
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

    public List<TradeDto> getTradeHistory() {
        List<Trade> trades = tradeRepository.findAll();
        List<TradeDto> tradeDtoHistory=trades.stream()
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

