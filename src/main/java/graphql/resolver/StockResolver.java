package graphql.resolver;

import graphql.kickstart.tools.GraphQLQueryResolver;
import model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.StockService;

@Component
public class StockResolver implements GraphQLQueryResolver {

    private final StockService stockService;

    @Autowired
    public StockResolver(StockService stockService) {
        this.stockService = stockService;
    }

    public Stock stock(String symbol) {
        return stockService.getStock(symbol);
    }
}
