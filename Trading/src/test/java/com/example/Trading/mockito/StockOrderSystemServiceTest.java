package com.example.Trading.mockito;

import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.OrderType;
import com.example.Trading.entity.Order;
import com.example.Trading.exception.InvalidOrderTypeException;
import com.example.Trading.repository.OrderRepository;
import com.example.Trading.service.StockOrderSystemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class StockOrderSystemServiceTest {

    @Mock
    private OrderRepository orderRepository;

    private StockOrderSystemServiceImpl stockOrderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        stockOrderService = new StockOrderSystemServiceImpl(orderRepository, null);
    }

    @Test
    public void testAddOrderValid() {
        OrderDto orderDto = new OrderDto();
        orderDto.setStockSymbol("ABCDEF1234");
        orderDto.setOrderType(OrderType.BUY);
        orderDto.setPrice(50.0);
        orderDto.setQuantity(20);
        orderDto.setStatus("PENDING");

        Order mockSavedOrder = new Order();
        mockSavedOrder.setId(1L);
        mockSavedOrder.setStockSymbol(orderDto.getStockSymbol());
        mockSavedOrder.setOrderType(orderDto.getOrderType());
        mockSavedOrder.setPrice(orderDto.getPrice());
        mockSavedOrder.setQuantity(orderDto.getQuantity());
        mockSavedOrder.setStatus(orderDto.getStatus());
        mockSavedOrder.setTimestamp(LocalDateTime.now());

        when(orderRepository.save(any(Order.class))).thenReturn(mockSavedOrder);

        Order savedOrder = stockOrderService.addOrder(orderDto);

        assertEquals(mockSavedOrder, savedOrder);
    }

}

