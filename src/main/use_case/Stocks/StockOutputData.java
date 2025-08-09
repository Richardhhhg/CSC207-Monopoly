package main.use_case.Stocks;

import main.entity.Stocks.Stock;
import main.entity.players.Player;

public abstract class StockOutputData {
    private final Player player;
    private final Stock stock;
    private final boolean allowBuy;

    public StockOutputData(Player player, Stock stock, boolean allowBuy) {
        this.player = player;
        this.stock = stock;
        this.allowBuy = allowBuy;
    }

    public Player getPlayer() {
        return player;
    }

    public Stock getStock() {
        return stock;
    }

    public boolean isAllowBuy() {
        return allowBuy;
    }

    public String getTicker() {
        return stock.getTicker();
    }

    public float getPrice() {
        return stock.getCurrentPrice();
    }

    public float getChange() {
        return stock.getChange();
    }

    public int getQuantity() {
        return player.getStocks().getOrDefault(stock, 0);
    }
}
