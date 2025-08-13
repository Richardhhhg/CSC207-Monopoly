package main.interface_adapter.StockMarket;

import main.use_case.Stocks.StockOutputBoundary;
import main.use_case.Stocks.StockOutputData;

public class StockPresenter implements StockOutputBoundary {
    private final StockPlayerViewModel stockPlayerViewModel;

    public StockPresenter(StockPlayerViewModel stockPlayerViewModel) {
        this.stockPlayerViewModel = stockPlayerViewModel;
    }

    public void execute(StockOutputData stockOutputData) {
        final StockPlayerState stockPlayerState = stockPlayerViewModel.getState();
        final StockState stockState = stockPlayerState.getStockState();
        stockState.setAllowBuy(stockOutputData.isAllowBuy());
        stockState.setTicker(stockOutputData.getTicker());
        stockState.setPrice(stockOutputData.getPrice());
        stockState.setChange(stockOutputData.getChange());
        stockPlayerState.setQuantity(stockOutputData.getQuantity());
        System.out.println(stockOutputData.getQuantity());
        stockPlayerState.setPlayer(stockOutputData.getPlayer());
        stockPlayerViewModel.firePropertyChanged();

    }
}
