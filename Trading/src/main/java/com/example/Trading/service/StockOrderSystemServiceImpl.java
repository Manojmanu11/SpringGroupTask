package com.example.Trading.service;

import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.TradeDto;
import com.example.Trading.dto.UpdatedPrice;
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
    public UpdatedPrice updatePrice(OrderDto req) {
        List<Order> stockOrders = orderRepository.findByStockSymbol(req.getStockSymbol());

        if (!stockOrders.isEmpty()) {
            for (Order order : stockOrders) {
                order.setPrice(req.getPrice());
                orderRepository.save(order);

                UpdatedPrice priceDTO = new UpdatedPrice();
                priceDTO.setInfoMsg("Successfully Updated");
                priceDTO.setStockSymbol(order.getStockSymbol());
                priceDTO.setPrice(order.getPrice());
                return priceDTO;
            }
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
