package service;

import model.AlphaVantageResponse;
import model.GlobalQuote;
import model.Stock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AlphaVantageService {

    private static final String BASE_URL = "https://www.alphavantage.co/query";
    private final RestTemplate restTemplate;
    private final String apiKey;

    public AlphaVantageService(RestTemplateBuilder restTemplateBuilder,
                               @Value("${alphavantage.api.key}") String apiKey) {
        this.restTemplate = restTemplateBuilder.build();
        this.apiKey = apiKey;
    }

    public Stock fetchStock(String symbol) {
        String url = BASE_URL + "?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + apiKey;
        ResponseEntity<AlphaVantageResponse> responseEntity = restTemplate.getForEntity(url, AlphaVantageResponse.class);
        AlphaVantageResponse response = responseEntity.getBody();

        if (response != null && response.getGlobalQuote() != null) {
            GlobalQuote globalQuote = response.getGlobalQuote();
            return new Stock(globalQuote.getSymbol(), Float.parseFloat(globalQuote.getPrice()), Integer.parseInt(globalQuote.getVolume()));
        } else {
            throw new RuntimeException("Error fetching stock data");
        }
    }
}
