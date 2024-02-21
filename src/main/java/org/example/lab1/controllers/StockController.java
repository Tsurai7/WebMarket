package org.example.lab1.controllers;

import org.example.lab1.models.StockResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import reactor.core.publisher.Flux;
import org.example.lab1.services.StockService;

@RestController
@RequestMapping("/api/stocks")
public class StockController
{
    private final StockService stockService;

    public StockController(StockService stockService)
    {
        this.stockService = stockService;
    }

    @GetMapping("/getString")
    public String getString(@RequestParam String word)
    {
        return "Result: " + word;
    }

    @GetMapping("/daily")
    public Flux<StockResponse> getDailyInfo() {
        return stockService.getDailyInfo();
    }
}