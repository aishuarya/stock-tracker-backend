package model;

public class Stock {

    private String symbol;
    private float price;
    private int volume;

    public Stock() {
        // Default constructor required by frameworks like Jackson
    }

    public Stock(String symbol, float price, int volume) {
        this.symbol = symbol;
        this.price = price;
        this.volume = volume;
    }

    // Getters and setters
}
