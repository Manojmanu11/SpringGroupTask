package com.example.Trading.mockito;

import com.example.Trading.dto.OrderType;
import com.example.Trading.dto.TradeDto;
import com.example.Trading.entity.Trade;
import com.example.Trading.repository.TradeRepository;
import com.example.Trading.service.StockOrderSystemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class StockOrderSystemServiceTest {

    @Mock
    private TradeRepository tradeRepository;
    StockOrderSystemServiceImpl stockOrderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        stockOrderService = new StockOrderSystemServiceImpl();
        stockOrderService.setTradeRepository(tradeRepository);
    }


    @Test
    public void testGetTradeHistory() {
        List<Trade> sampleTrades = new ArrayList<>();
        sampleTrades.add(createSampleTrade("AAPL", OrderType.BUY, 150.0, 100, LocalDateTime.now()));
        sampleTrades.add(createSampleTrade("GOOGL", OrderType.SELL, 2500.0, 50, LocalDateTime.now()));

        // Mock the behavior of the tradeRepository to return the sampleTrades list
        when(tradeRepository.findAll()).thenReturn(sampleTrades);

        // Call the method to be tested
        List<TradeDto> tradeHistory = stockOrderService.getTradeHistory();

        // Verify that the method under test produced the expected results
        assertEquals(sampleTrades.size(), tradeHistory.size());

    }
    private Trade createSampleTrade(String stockSymbol, OrderType orderType, double price, int quantity, LocalDateTime timestamp) {
        Trade trade = new Trade();
        trade.setStockSymbol(stockSymbol);
        trade.setOrderType(orderType);
        trade.setPrice(price);
        trade.setQuantity(quantity);
        trade.setTimestamp(timestamp);
        return trade;
    }


}