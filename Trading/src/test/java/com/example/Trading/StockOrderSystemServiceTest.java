package com.example.Trading;

import com.example.Trading.dto.OrderDto;
import com.example.Trading.dto.UpdatedPriceDto;
import com.example.Trading.entity.Order;
import com.example.Trading.repository.OrderRepository;
import com.example.Trading.service.StockOrderSystemService;
import com.example.Trading.service.StockOrderSystemServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;


public class StockOrderSystemServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Autowired
    private StockOrderSystemServiceImpl stockOrderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
public void testUpdatePriceSuccess() {
        OrderDto request = new OrderDto();
        request.setStockSymbol("BAJAJ");
        request.setPrice(100.0);

        Order order = new Order();
        order.setStockSymbol("BAJAJ");
        UpdatedPriceDto expectedResponse = new UpdatedPriceDto();
        expectedResponse.setInfoMsg("Successfully Updated");
        expectedResponse.setStockSymbol("BAJAJ");
        expectedResponse.setPrice(100.0);
        System.out.println("The expectedResp " + expectedResponse);

        Mockito.when(orderRepository.findByStockSymbol("BAJAJ")).thenReturn(Collections.singletonList(order));
        Mockito.when(orderRepository.save(order)).thenReturn(order);

        UpdatedPriceDto response = stockOrderService.updatePrice(request);
        System.out.println("UpdatedPrice Response " + response);

        Assert.assertEquals(expectedResponse, response);
        }
        }