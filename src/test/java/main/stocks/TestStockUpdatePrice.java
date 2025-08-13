package main.stocks;

import main.entity.stocks.Stock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestStockUpdatePrice {
    @Test
    void testUpdatePriceChangesCurrentPrice() {
        Stock stock = new Stock("AAPL", 100.0, 0.1, 1.0);
        float oldPrice = stock.getCurrentPrice();
        stock.updatePrice();
        float newPrice = stock.getCurrentPrice();
        assertNotEquals(oldPrice, newPrice, "Stock price should change after updatePrice()");
    }

    @Test
    void testUpdatePriceUpdatesPercentChange() {
        Stock stock = new Stock("GOOG", 200.0, 0.1, 1.0);
        float oldChange = stock.getChange();
        stock.updatePrice();
        float newChange = stock.getChange();
        // The percent change should be updated to a new value
        assertNotEquals(oldChange, newChange, "Percent change should update after updatePrice()");
    }

    @Test
    void testUpdatePriceMultipleTimes() {
        Stock stock = new Stock("MSFT", 150.0, 0.1, 1.0);
        float initialPrice = stock.getCurrentPrice();
        boolean priceChanged = false;
        for (int i = 0; i < 10; i++) {
            stock.updatePrice();
            if (stock.getCurrentPrice() != initialPrice) {
                priceChanged = true;
                break;
            }
        }
        assertTrue(priceChanged, "Stock price should change after multiple updates");
    }
}
