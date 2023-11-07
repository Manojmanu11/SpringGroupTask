package com.example.Trading;

import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.UpdatedPriceDto;
import com.example.Trading.entity.Order;
import com.example.Trading.repository.OrderRepository;

import com.example.Trading.constants.StringConstants;
import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.OrderType;
import com.example.Trading.dto.UpdatedPriceDto;
import com.example.Trading.entity.Order;
import com.example.Trading.repository.OrderRepository;
import com.example.Trading.service.StockOrderSystemService;

import com.example.Trading.service.StockOrderSystemServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;


@SpringBootTest
public class StockOrderSystemServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private StockOrderSystemServiceImpl stockOrderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StockOrderSystemServiceTest {

    @Mock
    private OrderRepository orderRepository;


    private StockOrderSystemService stockOrderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
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

    @Test
    public void testExecuteOrders() {
        // Mock buy and sell orders
        List<Order> buyOrders = new ArrayList<>();
        List<Order> sellOrders = new ArrayList<>();


        // Add mock buy and sell orders to the lists

        when(orderRepository.findBuyOrders()).thenReturn(buyOrders);
        when(orderRepository.findSellOrders()).thenReturn(sellOrders);

        // Mock user objects


        // Create mock buy and sell orders and associate them with users
        Order buyOrder = createMockOrder("TECHM", 10.0, StringConstants.PENDING, 10);
        Order sellOrder = createMockOrder("TECHM", 9.0, StringConstants.PENDING, 15);

        // Add the buy and sell orders to the respective lists
        buyOrders.add(buyOrder);
        sellOrders.add(sellOrder);


        // Call the executeOrders method
        stockOrderService.matchOrder();

        // Verify that expected methods were called
        verify(orderRepository).findBuyOrders();
        verify(orderRepository).findSellOrders();

        // Assert that your business logic works correctly
        assertEquals(0, buyOrder.getQuantity());
        assertEquals(5, sellOrder.getQuantity());
    }

    // Helper method to create a mock order
    private Order createMockOrder(String stockSymbol, double price, String status, int quantity) {
        Order order = new Order();
        order.setStockSymbol(stockSymbol);
        order.setPrice(price);
        order.setStatus(status);
        order.setQuantity(quantity);
        return order;
    }

    @Test
    public void testUpdatePriceSuccess() {
        OrderDto request = new OrderDto();
        request.setStockSymbol("BAJAJ");

        request.setPrice(110.0);
        request.setPrice(100.0);


        Order order = new Order();
        order.setStockSymbol("BAJAJ");
        UpdatedPriceDto expectedResponse = new UpdatedPriceDto();
        expectedResponse.setInfoMsg("Successfully Updated");
        expectedResponse.setStockSymbol("BAJAJ");

        expectedResponse.setPrice(110.0);
        expectedResponse.setPrice(100.0);

        System.out.println("The expectedResp " + expectedResponse);

        Mockito.when(orderRepository.findByStockSymbol("BAJAJ")).thenReturn(Collections.singletonList(order));
        Mockito.when(orderRepository.save(order)).thenReturn(order);

        UpdatedPriceDto response = stockOrderService.updatePrice(request);
        System.out.println("UpdatedPrice Response " + response);

        Assert.assertEquals(expectedResponse, response);
    }
}



