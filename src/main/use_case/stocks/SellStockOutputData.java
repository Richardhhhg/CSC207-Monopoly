package main.use_case.stocks;

import main.entity.players.AbstractPlayer;
import main.entity.stocks.Stock;

public class SellStockOutputData extends AbstractStockOutputData {

    public SellStockOutputData(AbstractPlayer player, Stock stock, boolean allowBuy) {
        super(player, stock, allowBuy);
    }
}
