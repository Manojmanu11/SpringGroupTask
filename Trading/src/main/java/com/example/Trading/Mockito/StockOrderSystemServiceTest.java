package com.example.Trading.Mockito;

import com.example.Trading.constants.StringConstants;
import com.example.Trading.repository.OrderRepository;
import com.example.Trading.repository.TradeRepository;
import com.example.Trading.service.StockOrderSystemService;
import com.example.Trading.service.StockOrderSystemServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.annotation.Order;


import static org.hibernate.validator.internal.util.Contracts.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class StockOrderSystemServiceTest {

    @InjectMocks
    private StockOrderSystemServiceImpl stockOrderSystemService;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
}


//    @Test
//    public void testIsMatchingOrders() {
//        // Create mock orders for testing
//        Order buyOrder = new Order("ABC", 100.0, 10, StringConstants.PENDING);
//        Order sellOrder = new Order("XYZ", 95.0, 15, StringConstants.PENDING);
//
//        // Call the method to be tested
//        boolean result = stockOrderSystemService.is(buyOrder, sellOrder);
//
//        // Verify the result is as expected
//        assertTrue(result);
//    }
//}