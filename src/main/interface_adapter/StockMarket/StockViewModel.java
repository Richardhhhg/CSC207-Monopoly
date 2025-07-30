package main.interface_adapter.StockMarket;

import main.entity.Stocks.Stock;
import main.entity.players.Player;

/**
 * Viewmodel for Individual Stock held by a player.
 */
public class StockViewModel {
    private final Stock stock;
    private final Player player;

    public StockViewModel(Stock stock, Player player) {
        this.stock = stock;
        this.player = player;
    }

    public String getTicker() {
        return stock.getTicker();
    }

    public double getPrice() {
        return stock.getCurrentPrice();
    }

    public int getAmount() {
        return player.getStockQuantity(stock);
    }

    public double getChange() {
        return stock.getChange();
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public Stock getStock() {
        return stock;
    }
}
