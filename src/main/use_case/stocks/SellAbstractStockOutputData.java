package main.use_case.stocks;

import main.entity.players.Player;
import main.entity.stocks.Stock;

public class SellAbstractStockOutputData extends AbstractStockOutputData {

    public SellAbstractStockOutputData(Player player, Stock stock, boolean allowBuy) {
        super(player, stock, allowBuy);
    }
}
