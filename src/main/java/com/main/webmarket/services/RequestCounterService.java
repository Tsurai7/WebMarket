package com.main.webmarket.services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Scope("singleton")
public class RequestCounterService {
    private final AtomicInteger requestsCount = new AtomicInteger(0);

    public synchronized void incrementRequestsCount() {
        requestsCount.incrementAndGet();
    }

    public synchronized int getRequestsCount() {
        return requestsCount.get();
    }
}
