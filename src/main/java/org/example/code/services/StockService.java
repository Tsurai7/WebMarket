package org.example.code.services;

import lombok.AllArgsConstructor;
import org.example.code.entities.Stock;
import org.example.code.models.StockResponse;
import org.example.code.models.TimeSeriesData;
import org.example.code.repositories.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@AllArgsConstructor
public class StockService
{
    private final StockRepository stockRepository;
    private static final String API_KEY = "QHFJAP4MXU0RPMN4";
    private static final String BASE_URL =  "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=%s&interval=5min&apikey=" + API_KEY;

    public void initDb() {

        List<String> tickers = List.of("AAPL", "GOOGL", "MSFT", "AMZN", "META");

        WebClient client = WebClient.create();

        for(String ticker : tickers) {

            String apiUrl = String.format(BASE_URL, ticker);

            StockResponse response =
            client.get()
            .uri(apiUrl)
            .retrieve()
            .bodyToMono(StockResponse.class).block();

            TimeSeriesData data = response.getTimeSeries().values().iterator().next();
            Stock stock = new Stock(ticker, data.getOpen(), data.getHigh(), data.getLow(), data.getClose(), data.getVolume());

            stockRepository.save(stock);
        }
    }

    public Flux<StockResponse> getDailyInfo(String ticker)
    {
        WebClient client = WebClient.create();

        String apiUrl = String.format(BASE_URL, ticker);

        return client.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToFlux(StockResponse.class);
    }
}
