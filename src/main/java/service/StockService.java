package service;

import com.github.benmanes.caffeine.cache.Cache;
import model.Stock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Caffeine;

@Service
public class StockService {

    private final AlphaVantageService alphaVantageService;
    private final Cache<String, Stock> stockCache;

    public StockService(AlphaVantageService alphaVantageService,
                        @Value("${cache.maximumSize}") int maximumSize,
                        @Value("${cache.expireAfterAccessSeconds}") long expireAfterAccessSeconds) {
        this.alphaVantageService = alphaVantageService;
        this.stockCache = Caffeine.newBuilder()
                .maximumSize(maximumSize)
                .expireAfterAccess(expireAfterAccessSeconds, TimeUnit.SECONDS)
                .build();
    }

    @Cacheable(cacheNames = "stocks")
    public Stock getStock(String symbol) {
        return stockCache.get(symbol, s -> alphaVantageService.fetchStock(symbol));
    }
}
