package main.use_case.stocks;

import main.entity.players.Player;
import main.entity.stocks.Stock;

public class SellStock {
    /**
     * Executes the use case for selling a stock.
     * @param player  Player who is selling the stock.
     * @param stock   Stock that is being sold.
     * @param quantity Quantity of stock being sold.
     * @throws IllegalArgumentException when player can't sell stock or quantity is invalid.
     */
    public void execute(Player player, Stock stock, int quantity) throws IllegalArgumentException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (quantity > player.getStockQuantity(stock)) {
            throw new IllegalArgumentException("Cannot sell more stocks than owned");
        }

        player.sellStock(stock, quantity);
    }
}
