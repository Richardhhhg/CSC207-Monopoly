package main.interface_adapter.stock_market;

import main.entity.players.Player;
import main.entity.stocks.Stock;
import main.use_case.stocks.BuyStock;

public class StockBuyController {
    /**
     * Executes use case for buying stock.
     * @param player Player that is buying the stock.
     * @param stock Stock that is being bought.
     * @param quantity Quantity of stock being bought.
     * @throws IllegalArgumentException when quantity not valid or insufficient funds.
     */
    public void execute(Player player, Stock stock, int quantity) throws IllegalArgumentException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (player.getMoney() < stock.getCurrentPrice() * quantity) {
            throw new IllegalArgumentException("Insufficient funds to buy stocks");
        }

        final BuyStock buyStock = new BuyStock();
        buyStock.execute(player, stock, quantity);
    }
}
