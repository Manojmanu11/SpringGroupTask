package com.example.Trading.service;

import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.TradeDto;
import com.example.Trading.dto.UpdatedPriceDto;
import com.example.Trading.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.Trading.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Service
@Slf4j
public class StockOrderSystemServiceImpl implements StockOrderSystemService {
    @Autowired
    private  OrderRepository orderRepository;
    int additionalValue = 0;


    @Override
    public Order addOrder(OrderDto orderDto) {
        return null;
    }

    public UpdatedPriceDto updatePrice(OrderDto req) {
        List<Order> stockOrders = orderRepository.findByStockSymbol(req.getStockSymbol());

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
    //    @Scheduled(fixedRate = 60000) // Scheduled task to clear the updated price every minute
//    public void clearUpdatedPrice() {
//        log.info("The updated price has been cleared");
//        additionalValue = 0;
//    }

    @Override
    public OrderDto matchOrders(OrderDto orderDto) {
        return null;
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

