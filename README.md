# Stock market information app

## SONAR OVERVIEW
https://sonarcloud.io/summary/overall?id=Tsurai7_Lab1

# What is it?
This application provides the most relevant information about stock market changes.

## Api source 
    https://www.alphavantage.co/documentation/#
## Query examples
    GET http://localhost:8080/api/stocks/getString?word=123
***
    GET http://localhost:8080/api/stocks/daily
## Response examples
    { "AAPL": 123 }
***
    "Meta Data": {
        "1. Information": "Intraday (5min) open, high, low, close prices and volume",
        "2. Symbol": "AAPL",
        "3. Last Refreshed": "2024-02-23 19:55:00",
        "4. Interval": "5min",
        "5. Output Size": "Compact",
        "6. Time Zone": "US/Eastern"
        },
        "Time Series (5min)": {
            "2024-02-23 19:55:00": {
                "1. open": "182.3500",
                "2. high": "182.3500",
                "3. low": "182.2300",
                "4. close": "182.2400",
                "5. volume": "6326"
            },
            "2024-02-23 19:50:00": {
                "1. open": "182.2900",
                "2. high": "182.3800",
                "3. low": "182.2900",
                "4. close": "182.3200",
                "5. volume": "2030"
            }, 
            ...
## Prerequisites
- Java Development Kit (JDK) 17 or later
- Apache Maven 3.9.6