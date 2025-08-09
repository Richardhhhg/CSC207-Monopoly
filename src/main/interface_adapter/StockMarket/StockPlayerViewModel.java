package main.interface_adapter.StockMarket;

import interface_adapter.ViewModel;
import main.entity.players.Player;

public class StockPlayerViewModel extends ViewModel<StockPlayerState> {
    public StockPlayerViewModel(StockState stockstate) {
        super("Stock Player");
        setState(new StockPlayerState(stockstate));
    }

}
