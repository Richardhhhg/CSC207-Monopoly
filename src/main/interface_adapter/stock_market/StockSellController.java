package main.interface_adapter.stock_market;

import main.entity.players.Player;
import main.entity.stocks.Stock;
import main.use_case.stocks.SellStock;

public class StockSellController {

    /**
     * Executes the stock selling operation for a player.
     *
     * @param player   The player who is selling the stock.
     * @param stock    The stock to be sold.
     * @param quantity The number of stocks to sell.
     * @throws IllegalArgumentException if the quantity is invalid or if the player does not have enough stocks to sell.
     */
    public void execute(Player player, Stock stock, int quantity) throws IllegalArgumentException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (player.getStockQuantity(stock) < quantity) {
            throw new IllegalArgumentException("Insufficient stocks to sell");
        }

        final SellStock sellStock = new SellStock();
        sellStock.execute(player, stock, quantity);
    }
}
