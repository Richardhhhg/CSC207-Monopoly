package main.interface_adapter.StockMarket;

import main.entity.Stocks.Stock;
import main.entity.players.Player;

public class StockPresenter {
    public StockViewModel execute(Stock stock, Player player) {
        return new StockViewModel(stock, player);
    }
}
