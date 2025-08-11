package main.use_case.Stocks;

import main.entity.players.Player;
import main.entity.Stocks.Stock;

public class BuyStock {
    public void execute(Player player, Stock stock, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (player.getMoney() < stock.getCurrentPrice() * quantity) {
            throw new IllegalArgumentException("Insufficient funds to buy stocks");
        }

        player.buyStock(stock, quantity);
    }
}
