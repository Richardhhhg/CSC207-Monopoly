package main.use_case.Game;

import main.Constants.Config;
import main.Constants.Constants;
import main.data_access.StockMarket.StockInfoDataOutputObject;
import main.entity.Game;
import main.entity.Stocks.Stock;
import main.entity.Stocks.StockInformationRetriever;
import main.use_case.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Use case for initializing stocks in the game.
 * If you want to use real stock data, set USE_API to true.
 */
public class GameInitializeStocks {
    private static final boolean USE_API = false; // Set to true to use API for stock data
    private final Game game;

    public GameInitializeStocks(Game game) {
        this.game = game;
    }

    public void execute() {
        List<Stock> stocks = new ArrayList<>();
        List<Player> players = game.getPlayers();

        if (USE_API) {
            StockInformationRetriever stockInfoRetriever = new StockInformationRetriever(Config.getApiKey());
            try {
                // Create stocks using the stock information retriever
                stocks = stockInfoRetriever.createStocks(Constants.STOCK_NAME_FILE);
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize stocks from API", e);
            }
        } else {
            for (int i = 0; i < 5; i++) {
                StockInfoDataOutputObject info = new StockInfoDataOutputObject("TEST_" + i, 100, 10, 30);
                Stock stock = new Stock(info);
                stocks.add(stock);
            }
        }
        for (Player player: players) {
            player.initializeStocks(stocks);
        }

        game.setStocks(stocks);
    }
}
