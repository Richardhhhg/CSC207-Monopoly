package main.use_case.Stocks;

import main.entity.Stock;
import main.entity.players.Player;

public class SellStock {
    public void execute(Player player, Stock stock, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (quantity > player.getStockQuantity(stock)) {
            throw new IllegalArgumentException("Cannot sell more stocks than owned");
        }

        player.sellStock(stock, quantity);
    }
}
