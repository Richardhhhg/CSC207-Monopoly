package main.use_case.Stocks;

import main.entity.Stocks.Stock;
import main.entity.players.Player;

public class SellStockInputData {
    private final Player player;
    private final Stock stock;
    private final int quantity;

    public SellStockInputData(Player player, Stock stock, int quantity) {
        this.player = player;
        this.stock = stock;
        this.quantity = quantity;
    }

    public Player getPlayer() {
        return player;
    }

    public Stock getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }
}
