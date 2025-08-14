package main.use_case.stocks;

import main.entity.players.AbstractPlayer;
import main.entity.stocks.Stock;

public abstract class AbstractStockOutputData {
    private final AbstractPlayer abstractPlayer;
    private final Stock stock;
    private final boolean allowBuy;

    public AbstractStockOutputData(AbstractPlayer abstractPlayer, Stock stock, boolean allowBuy) {
        this.abstractPlayer = abstractPlayer;
        this.stock = stock;
        this.allowBuy = allowBuy;
    }

    public AbstractPlayer getPlayer() {
        return abstractPlayer;
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
        return abstractPlayer.getStocks().getOrDefault(stock, 0);
    }
}
