package main.interface_adapter.StockMarket;

import main.entity.Stocks.Stock;
import main.entity.players.Player;

public class StockPlayerState {
    private final StockState stockstate;
    private int quantity;
    private Player player;
    private Stock stock;

    public StockPlayerState(StockState stockstate) {
        this.stockstate = stockstate;
    }

    public StockState getStockState() {
        return stockstate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
