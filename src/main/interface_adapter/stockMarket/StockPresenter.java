package main.interface_adapter.stockMarket;

import main.entity.stocks.Stock;
import main.entity.players.Player;

public class StockPresenter {
    public StockViewModel execute(Stock stock, Player player, boolean allowBuy) {
        return new StockViewModel(stock, player, allowBuy);
    }
}
