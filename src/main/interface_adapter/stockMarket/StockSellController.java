package main.interface_adapter.stockMarket;

import main.entity.stocks.Stock;
import main.entity.players.Player;
import main.use_case.stocks.SellStock;

public class StockSellController {
    public void execute(Player player, Stock stock, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (player.getStockQuantity(stock) < quantity) {
            throw new IllegalArgumentException("Insufficient stocks to sell");
        }

        SellStock sellStock = new SellStock();
        sellStock.execute(player, stock, quantity);
    }
}
