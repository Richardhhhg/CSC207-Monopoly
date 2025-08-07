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
import main.entity.players.Player;
import main.entity.stocks.Stock;
import main.interface_adapter.stock_market.StockFactory;

public class GameInitializeStocks {
    private static final boolean USE_API = Constants.USE_API;
    private final Game game;

    public GameInitializeStocks(Game game) {
        this.game = game;
    }

    /**
     * Initializes stocks for the game and assigns them to each player.
     * @throws IOException if there is an error retrieving stock data.
     */
    public void execute() throws IOException {
        final List<Stock> stocks = new ArrayList<>();
        final List<Player> players = game.getPlayers();
        final StockFactory stockFactory = new StockFactory();

        if (USE_API) {
            final ApiStockInfoRepository stockInfoRetriever = new ApiStockInfoRepository(Config.getApiKey());
            try {
                final List<String> tickers = stockInfoRetriever.loadTickerSymbols(Constants.STOCK_NAME_FILE);
                for (String ticker : tickers) {
                    final StockInfoDataOutputObject info = stockInfoRetriever.getStockInfo(ticker);
                    final Stock stock = stockFactory.execute(info);
                    stocks.add(stock);
                }
            }
            catch (InterruptedException exception) {
                throw new IOException("Failed to initialize stocks from API", exception);
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
        for (Player player: players) {
            player.initializeStocks(stocks);
        }

        game.setStocks(stocks);
    }
}
