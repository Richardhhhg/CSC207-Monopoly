package main.use_case.stocks;

import main.entity.players.AbstractPlayer;
import main.entity.stocks.Stock;

public class SellStockInputData {
    private final AbstractPlayer abstractPlayer;
    private final Stock stock;
    private final int quantity;

    public SellStockInputData(AbstractPlayer abstractPlayer, Stock stock, int quantity) {
        this.abstractPlayer = abstractPlayer;
        this.stock = stock;
        this.quantity = quantity;
    }

    public AbstractPlayer getPlayer() {
        return abstractPlayer;
    }

    public Stock getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }
}
