package main.use_case.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.constants.Config;
import main.constants.Constants;
import main.data_access.stock_market.ApiStockInfoRepository;
import main.data_access.stock_market.DefaultStockInfoRepository;
import main.data_access.stock_market.StockInfoDataOutputObject;
import main.entity.Game;
import main.entity.players.AbstractPlayer;
import main.entity.stocks.Stock;
import main.interface_adapter.stock_market.StockFactory;

public class GameInitializeStocks {
    private static final boolean USE_API = Constants.USE_STOCK_API;
    private final Game game;

    public GameInitializeStocks(Game game) {
        this.game = game;
    }

    /**
     * Initializes the stocks for the game.
     *
     * @throws IOException if there is an error retrieving stock information from the API
     * @throws InterruptedException if the thread is interrupted while waiting for stock information
     */
    public void execute() throws IOException, InterruptedException {
        final List<Stock> stocks = new ArrayList<>();
        final List<AbstractPlayer> players = game.getPlayers();
        final StockFactory stockFactory = new StockFactory();

        if (USE_API) {
            final ApiStockInfoRepository stockInfoRetriever = new ApiStockInfoRepository(Config.getApiKey());
            final List<String> tickers = stockInfoRetriever.loadTickerSymbols(Constants.STOCK_NAME_FILE);
            for (String ticker : tickers) {
                final StockInfoDataOutputObject info = stockInfoRetriever.getStockInfo(ticker);
                final Stock stock = stockFactory.execute(info);
                stocks.add(stock);
            }
        }
        else {
            final DefaultStockInfoRepository stockInfoRetriever = new DefaultStockInfoRepository();
            for (int i = 0; i < Constants.NUM_STOCKS; i++) {
                final StockInfoDataOutputObject info = stockInfoRetriever.getStockInfo("TEST_" + (i + 1));
                final Stock stock = stockFactory.execute(info);
                stocks.add(stock);
            }
        }
        for (AbstractPlayer player: players) {
            player.initializeStocks(stocks);
        }

        game.setStocks(stocks);
    }
}
