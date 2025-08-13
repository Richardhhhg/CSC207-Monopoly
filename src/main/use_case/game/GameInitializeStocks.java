package main.use_case.game;

import main.constants.Config;
import main.constants.Constants;
import main.data_access.stock_market.DefaultStockInfoRepository;
import main.data_access.stock_market.StockInfoDataOutputObject;
import main.entity.Game;
import main.entity.stocks.Stock;
import main.data_access.stock_market.ApiStockInfoRepository;
import main.interface_adapter.stock_market.StockFactory;
import main.entity.players.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;

public class GameInitializeStocks {
    private static final boolean USE_API = Constants.USE_STOCK_API; // Set to true to use API for stock data
    private final Game game;

    public GameInitializeStocks(Game game) {
        this.game = game;
    }

    public void execute() {
        List<Stock> stocks = new ArrayList<>();
        List<AbstractPlayer> players = game.getPlayers();
        StockFactory stockFactory = new StockFactory();

        if (USE_API) {
            ApiStockInfoRepository stockInfoRetriever = new ApiStockInfoRepository(Config.getApiKey());
            try {
                List<String> tickers = stockInfoRetriever.loadTickerSymbols(Constants.STOCK_NAME_FILE);
                for (String ticker : tickers) {
                    StockInfoDataOutputObject info = stockInfoRetriever.getStockInfo(ticker);
                    Stock stock = stockFactory.execute(info);
                    stocks.add(stock);
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize stocks from API", e);
            }
        } else {
            DefaultStockInfoRepository stockInfoRetriever = new DefaultStockInfoRepository();
            for (int i = 0; i < 5; i++) {
                StockInfoDataOutputObject info = stockInfoRetriever.getStockInfo("TEST_" + (i + 1));
                Stock stock = stockFactory.execute(info);
                stocks.add(stock);
            }
        }
        for (AbstractPlayer player: players) {
            player.initializeStocks(stocks);
        }

        game.setStocks(stocks);
    }
}