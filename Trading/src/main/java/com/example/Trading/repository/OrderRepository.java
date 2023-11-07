package com.example.Trading.repository;

import com.example.Trading.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByStockSymbol(String stockSymbol);
    
    @Query("SELECT o FROM Order o WHERE o.orderType = 'BUY'")
    List<Order> findBuyOrders();

    @Query("SELECT o FROM Order o WHERE o.orderType = 'SELL'")
    List<Order> findSellOrders();
   

}
