package main.use_case.stocks;

import main.entity.players.Player;
import main.entity.stocks.Stock;

public class SellStock {
    /**
     * Executes use case for selling stock.
     * @param player Player that is selling the stock
     * @param stock Stock that is being sold
     * @param quantity Quantity of stock being sold.
     * @throws IllegalArgumentException when quantity invalid or when player as insufficient stocks to sell.
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
