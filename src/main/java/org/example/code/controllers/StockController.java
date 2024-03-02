package org.example.code.controllers;

import lombok.AllArgsConstructor;
import org.example.code.models.StockResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import org.example.code.services.StockService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/stocks")
public class StockController
{
    private final StockService stockService;

    @GetMapping("/daily")
    public Flux<StockResponse> getDailyInfo(@RequestParam String ticker) {
            return stockService.getDailyInfo(ticker);
    }
}