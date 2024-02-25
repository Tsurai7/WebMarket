package org.example.lab1.services;

import org.example.lab1.models.StockResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class StockService
{
    private static final String ApiKey = "QHFJAP4MXU0RPMN4";
    private static final String BaseUrl =  "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=AAPL&interval=5min&apikey=" + ApiKey;

    public Flux<StockResponse> getDailyInfo()
    {
        WebClient client = WebClient.create();

        return client.get()
                .uri(BaseUrl)
                .retrieve()
                .bodyToFlux(StockResponse.class);
    }
}
