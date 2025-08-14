package main.interface_adapter.stock_market;

import main.entity.players.AbstractPlayer;
import main.entity.stocks.Stock;

public class StockPlayerState {
    private final StockState stockstate;
    private int quantity;
    private AbstractPlayer abstractPlayer;
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

    public AbstractPlayer getPlayer() {
        return abstractPlayer;
    }

    public void setPlayer(AbstractPlayer abstractPlayer) {
        this.abstractPlayer = abstractPlayer;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
