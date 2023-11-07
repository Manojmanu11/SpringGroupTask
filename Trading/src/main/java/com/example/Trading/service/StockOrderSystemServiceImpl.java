package com.example.Trading.service;

import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.TradeDto;
import com.example.Trading.dto.UpdatedPriceDto;
import com.example.Trading.entity.Order;
import com.example.Trading.entity.Trade;
import com.example.Trading.repository.OrderRepository;
import com.example.Trading.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockOrderSystemServiceImpl implements StockOrderSystemService{
     @Autowired
     OrderRepository orderRepository;
     @Autowired
     TradeRepository tradeRepository;

    @Override
    public OrderDto addOrder(OrderDto orderDto) {
        return null;
    }

   // UpdateService for stockSymbols UpdatePrice
    @Override
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
