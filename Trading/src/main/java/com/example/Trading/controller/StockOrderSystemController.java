package com.example.Trading.controller;

import com.example.Trading.constants.ApiConstants;
import com.example.Trading.dto.PortfolioDto;
import com.example.Trading.service.StockOrderSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class StockOrderSystemController {
    @Autowired
    StockOrderSystemService stockOrderSystemService;
    @GetMapping(ApiConstants.Get_Portfolio)
    public List<PortfolioDto> getPortfolio(){
        return stockOrderSystemService.getPortfolio();
    }
}
