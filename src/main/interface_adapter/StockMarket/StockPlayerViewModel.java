package main.interface_adapter.StockMarket;

import main.interface_adapter.ViewModel;

public class StockPlayerViewModel extends ViewModel<StockPlayerState> {
    public StockPlayerViewModel(StockState stockstate) {
        super("Stock Player");
        setState(new StockPlayerState(stockstate));
    }

}
