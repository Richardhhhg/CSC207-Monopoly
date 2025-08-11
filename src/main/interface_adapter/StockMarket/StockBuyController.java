package main.interface_adapter.StockMarket;

import main.entity.Stocks.Stock;
import main.entity.players.Player;
import main.use_case.Stocks.BuyStock;

public class StockBuyController {
    public void execute(Player player, Stock stock, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (player.getMoney() < stock.getCurrentPrice() * quantity) {
            throw new IllegalArgumentException("Insufficient funds to buy stocks");
        }

        BuyStock BuyStock = new BuyStock();
        BuyStock.execute(player, stock, quantity);
    }
}
