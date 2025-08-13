package main.use_case.stocks;

import main.entity.players.AbstractPlayer;
import main.entity.stocks.Stock;

public abstract class AbstractStockOutputData {
    private final AbstractPlayer player;
    private final Stock stock;
    private final boolean allowBuy;

    public AbstractStockOutputData(AbstractPlayer player, Stock stock, boolean allowBuy) {
        this.player = player;
        this.stock = stock;
        this.allowBuy = allowBuy;
    }

    public AbstractPlayer getPlayer() {
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
