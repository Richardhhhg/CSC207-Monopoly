package main.use_case.stocks;

import main.entity.players.AbstractPlayer;
import main.entity.stocks.Stock;

public class SellStockInputData {
    private final AbstractPlayer player;
    private final Stock stock;
    private final int quantity;

    public SellStockInputData(AbstractPlayer player, Stock stock, int quantity) {
        this.player = player;
        this.stock = stock;
        this.quantity = quantity;
    }

    public AbstractPlayer getPlayer() {
        return player;
    }

    public Stock getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }
}
