package org.example.code.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.example.code.entities.Stock;
import org.example.code.models.StockResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import org.example.code.services.StockService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/stocks")
public class StockController
{
    private final StockService stockService;

    @GetMapping("/getAll")
    public List<Stock> getAll() {
        return stockService.getAll();
    }

    @GetMapping("/getDaily")
    public Flux<StockResponse> getDailyInfo(@RequestParam String ticker) {
            return stockService.getDailyInfo(ticker);
    }
}