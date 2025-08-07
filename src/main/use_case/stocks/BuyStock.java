package main.use_case.stocks;

import main.entity.players.Player;
import main.entity.stocks.Stock;

public class BuyStock {
    /**
     * Executes the use case for buying a stock.
     * @param player Player that is buying the stock.
     * @param stock  Stock that is being bought.
     * @param quantity How much of the stock is being bought.
     * @throws IllegalArgumentException when quantity is invalid or player cannot afford.
     */
    public void execute(Player player, Stock stock, int quantity) throws IllegalArgumentException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (player.getMoney() < stock.getCurrentPrice() * quantity) {
            throw new IllegalArgumentException("Insufficient funds to buy stocks");
        }

        player.buyStock(stock, quantity);
    }
}
