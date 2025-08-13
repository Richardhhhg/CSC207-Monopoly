package main.use_case.game;

import main.entity.Game;
import main.entity.stocks.Stock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GameNextRoundTest {

    static class TestStock extends Stock {
        public boolean priceUpdated = false;
        public TestStock(String symbol, double currentPrice, double meanDailyReturnPct, double standardDeviationPct) {
            super(symbol, currentPrice, meanDailyReturnPct, standardDeviationPct);
        }
        @Override
        public void updatePrice() {
            priceUpdated = true;
        }
    }

    static class TestGame extends Game {
        private final List<Stock> stocks;
        public TestGame(List<Stock> stocks) {
            this.stocks = stocks;
        }
        @Override
        public List<Stock> getStocks() {
            return stocks;
        }
    }

    @Test
    void testStockPricesUpdated() {
        TestStock s1 = new TestStock("AAPL", 100.0, 0.01, 0.02);
        TestStock s2 = new TestStock("GOOG", 200.0, 0.01, 0.02);
        List<Stock> stocks = new ArrayList<>();
        stocks.add(s1);
        stocks.add(s2);

        TestGame game = new TestGame(stocks);
        GameNextRound useCase = new GameNextRound(game);
        useCase.execute();

        assertTrue(s1.priceUpdated, "Stock 1 should be updated");
        assertTrue(s2.priceUpdated, "Stock 2 should be updated");
    }
}