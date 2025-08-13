package main.use_case.stocks;

import main.entity.players.Player;
import main.entity.stocks.Stock;

public class BuyStockOutputData extends AbstractStockOutputData {

    public BuyStockOutputData(Player player, Stock stock, boolean allowBuy) {
        super(player, stock, allowBuy);
    }
}
