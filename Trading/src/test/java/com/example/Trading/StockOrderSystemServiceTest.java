package com.example.Trading;

import com.example.Trading.constants.StringConstants;
import com.example.Trading.dto.*;
import com.example.Trading.entity.Order;
import com.example.Trading.entity.Trade;
import com.example.Trading.repository.OrderRepository;
import com.example.Trading.repository.TradeRepository;
import com.example.Trading.service.StockOrderSystemService;
import com.example.Trading.service.StockOrderSystemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StockOrderSystemServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private TradeRepository tradeRepository;

    private StockOrderSystemService stockOrderService;

    private Order mockOrder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        stockOrderService = new StockOrderSystemServiceImpl(orderRepository, tradeRepository);
        mockOrder = createMockOrder("ABC", 10.0,OrderType.BUY, StringConstants.PENDING, 10);
    }

    // Helper method to create a mock order
    private Order createMockOrder(String stockSymbol, double price, OrderType orderType, String status, int quantity) {
        Order order = new Order();
        order.setStockSymbol(stockSymbol);
        order.setPrice(price);
        order.setStatus(status);
        order.setQuantity(quantity);
        order.setOrderType(orderType);
        return order;
    }

    @Test
    public void testAddOrderValid() {
        OrderDto orderDto = new OrderDto();
        orderDto.setStockSymbol("ABC");
        orderDto.setOrderType(OrderType.BUY);
        orderDto.setPrice(10.0);
        orderDto.setQuantity(5);

        Order mockSavedOrder = createMockOrder("ABC", 10.0,OrderType.BUY, StringConstants.PENDING, 5);
        when(orderRepository.save(any(Order.class))).thenReturn(mockSavedOrder);

        Order savedOrder = stockOrderService.addOrder(orderDto);
        assertEquals(mockSavedOrder, savedOrder);
    }

    @Test
    public void testUpdatePriceSuccess() {
        OrderDto request = new OrderDto();
        request.setStockSymbol("ABC");
        request.setOrderType(OrderType.BUY);
        request.setPrice(100.0);

        Order mockOrder = createMockOrder("ABC", 10.0,OrderType.BUY, StringConstants.PENDING, 10);

        UpdatedPriceDto expectedResponse = new UpdatedPriceDto();
        expectedResponse.setInfoMsg("Successfully Updated");
        expectedResponse.setStockSymbol("ABC");
        expectedResponse.setPrice(100.0);

        when(orderRepository.findByStockSymbol("ABC")).thenReturn(List.of(mockOrder));
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);

        UpdatedPriceDto response = stockOrderService.updatePrice(request);
        assertEquals(expectedResponse, response);
    }


    @Test
    void testGetPortfolio() {
        // Mock the behavior of orderRepository.findAll()
        when(orderRepository.findAll()).thenReturn(List.of(mockOrder));

        List<PortfolioDto> portfolioDtoList = stockOrderService.getPortfolio();

        assertEquals(1, portfolioDtoList.size());

        PortfolioDto portfolioDto = portfolioDtoList.get(0);
        assertEquals("ABC", portfolioDto.getStockSymbol());
        assertEquals(10, portfolioDto.getQuantity());
        assertEquals(StringConstants.PENDING, portfolioDto.getOrderStatus());
    }

    @Test
    public void testGetTradeHistory() {
        List<Trade> sampleTrades = Arrays.asList(
                createSampleTrade("AAPL", OrderType.BUY, 150.0, 100),
                createSampleTrade("GOOGL", OrderType.SELL, 2500.0, 50)
        );

        when(tradeRepository.findAll()).thenReturn(sampleTrades);

        List<TradeDto> tradeHistory = stockOrderService.getTradeHistory();

        assertEquals(sampleTrades.size(), tradeHistory.size());
    }

    @Test
    public void testExecuteOrders() throws NoSuchFieldException, IllegalAccessException {
        List<Order> buyOrders = new ArrayList<>();
        List<Order> sellOrders = new ArrayList<>();

        when(orderRepository.findBuyOrders()).thenReturn(buyOrders);
        when(orderRepository.findSellOrders()).thenReturn(sellOrders);

        // Create mock buy and sell orders and add them to the respective lists
        Order buyOrder = createMockOrder("ABC", 10.0,OrderType.BUY, StringConstants.PENDING, 10);
        Order sellOrder = createMockOrder("ABC", 9.0,OrderType.SELL, StringConstants.PENDING, 15);
        buyOrders.add(buyOrder);
        sellOrders.add(sellOrder);

        Field additionalValueField = StockOrderSystemServiceImpl.class.getDeclaredField("additionalValue");
        additionalValueField.setAccessible(true);
        additionalValueField.set(stockOrderService, 1);

        stockOrderService.matchOrder();

        verify(orderRepository).findBuyOrders();
        verify(orderRepository).findSellOrders();

        assertEquals(0, buyOrder.getQuantity());
        assertEquals(5, sellOrder.getQuantity());
    }


    private Trade createSampleTrade(String stockSymbol, OrderType orderType, double price, int quantity) {
        Trade trade = new Trade();
        trade.setStockSymbol(stockSymbol);
        trade.setOrderType(orderType);
        trade.setPrice(price);
        trade.setQuantity(quantity);
        trade.setTimestamp(LocalDateTime.now());
        return trade;
    }
}
