package main.data_access.StockMarket;

import main.entity.players.Player;
import main.entity.Stocks.Stock;
import main.use_case.Stocks.PlayerStockRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of PlayerStockRepository that manages player stock operations.
 * This class serves as the adapter between the use case layer and the entity layer.
 */
public class InMemoryPlayerStockRepository implements PlayerStockRepository {
    private final Map<String, Player> players;
    private final Map<String, Stock> stocks;

    public InMemoryPlayerStockRepository() {
        this.players = new HashMap<>();
        this.stocks = new HashMap<>();
    }

    /**
     * Registers a player in the repository.
     *
     * @param playerId the player's identifier
     * @param player the player entity
     */
    public void addPlayer(String playerId, Player player) {
        players.put(playerId, player);
    }

    /**
     * Registers a stock in the repository.
     *
     * @param stockTicker the stock ticker symbol
     * @param stock the stock entity
     */
    public void addStock(String stockTicker, Stock stock) {
        stocks.put(stockTicker, stock);
    }

    @Override
    public double getPlayerMoney(String playerId) {
        Player player = players.get(playerId);
        if (player == null) {
            throw new IllegalArgumentException("Player not found: " + playerId);
        }
        return player.getMoney();
    }

    @Override
    public int getPlayerStockQuantity(String playerId, String stockTicker) {
        Player player = players.get(playerId);
        Stock stock = stocks.get(stockTicker);
        
        if (player == null) {
            throw new IllegalArgumentException("Player not found: " + playerId);
        }
        if (stock == null) {
            throw new IllegalArgumentException("Stock not found: " + stockTicker);
        }
        
        return player.getStockQuantity(stock);
    }

    @Override
    public void buyStockForPlayer(String playerId, String stockTicker, int quantity, double totalCost) {
        Player player = players.get(playerId);
        Stock stock = stocks.get(stockTicker);
        
        if (player == null) {
            throw new IllegalArgumentException("Player not found: " + playerId);
        }
        if (stock == null) {
            throw new IllegalArgumentException("Stock not found: " + stockTicker);
        }
        
        player.buyStock(stock, quantity);
    }

    @Override
    public void sellStockForPlayer(String playerId, String stockTicker, int quantity, double totalSale) {
        Player player = players.get(playerId);
        Stock stock = stocks.get(stockTicker);
        
        if (player == null) {
            throw new IllegalArgumentException("Player not found: " + playerId);
        }
        if (stock == null) {
            throw new IllegalArgumentException("Stock not found: " + stockTicker);
        }
        
        player.sellStock(stock, quantity);
    }
}