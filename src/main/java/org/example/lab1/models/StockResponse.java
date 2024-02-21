package org.example.lab1.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class StockResponse {
    @JsonProperty("Meta Data")
    private MetaData metaData;

    @JsonProperty("Time Series (5min)")
    private Map<String, TimeSeriesData> timeSeries;
}