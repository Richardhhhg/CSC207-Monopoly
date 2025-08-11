package main.use_case.Stocks;

import main.entity.Stocks.Stock;
import main.entity.players.Player;

public class SellStockOutputData extends StockOutputData {

    public SellStockOutputData(Player player, Stock stock, boolean allowBuy) {
        super(player, stock, allowBuy);
    }
}
