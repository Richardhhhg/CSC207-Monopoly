package main.interface_adapter.StockMarket;

import main.entity.Stocks.Stock;
import main.entity.players.Player;

/**
 * Viewmodel for Individual Stock held by a player.
 * Updated to use DTOs while maintaining backward compatibility.
 */
public class StockViewModel {
    private final StockData stock;
    private final PlayerStockData playerStock;
    private final boolean allowBuy;
    
    // Backward compatibility fields
    private final Stock legacyStock;
    private final Player legacyPlayer;

    // New constructor using DTOs
    public StockViewModel(StockData stock, PlayerStockData playerStock, boolean allowBuy) {
        this.stock = stock;
        this.playerStock = playerStock;
        this.allowBuy = allowBuy;
        this.legacyStock = null;
        this.legacyPlayer = null;
    }

    // Backward compatibility constructor
    public StockViewModel(Stock stock, Player player, boolean allowBuy) {
        this.legacyStock = stock;
        this.legacyPlayer = player;
        this.allowBuy = allowBuy;
        this.stock = null;
        this.playerStock = null;
    }

    public String getTicker() {
        return stock != null ? stock.getTicker() : legacyStock.getTicker();
    }

    public double getPrice() {
        return stock != null ? stock.getCurrentPrice() : legacyStock.getCurrentPrice();
    }

    public int getAmount() {
        return playerStock != null ? playerStock.getStockQuantity() : legacyPlayer.getStockQuantity(legacyStock);
    }

    public double getChange() {
        return stock != null ? stock.getChange() : legacyStock.getChange();
    }
    
    public String getPlayerId() {
        return playerStock != null ? playerStock.getPlayerId() : legacyPlayer.getName();
    }
    
    // Returns StockData for new clean architecture code
    public StockData getStockData() {
        return stock;
    }

    // Returns Stock entity for backward compatibility
    public Stock getStock() {
        return legacyStock;
    }

    // Returns Stock entity for backward compatibility
    public Stock getLegacyStock() {
        return legacyStock;
    }

    // Backward compatibility method
    public Player getPlayer() {
        return legacyPlayer;
    }

    public boolean isAllowBuy() {
        return allowBuy;
    }

    public double getPlayerMoney() {
        return playerStock != null ? playerStock.getMoney() : legacyPlayer.getMoney();
    }
}
