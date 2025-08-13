package main.use_case.stocks;

import main.entity.players.Player;
import main.entity.stocks.Stock;

public class SellStockOutputData extends AbstractStockOutputData {

    public SellStockOutputData(Player player, Stock stock, boolean allowBuy) {
        super(player, stock, allowBuy);
    }
}
