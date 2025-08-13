package main.interface_adapter.stock_market;

import main.use_case.stocks.StockOutputBoundary;
import main.use_case.stocks.AbstractStockOutputData;

public class StockPresenter implements StockOutputBoundary {
    private final StockPlayerViewModel stockPlayerViewModel;

    public StockPresenter(StockPlayerViewModel stockPlayerViewModel) {
        this.stockPlayerViewModel = stockPlayerViewModel;
    }

    /**
     * Presents the stock information to the user.
     *
     * @param abstractStockOutputData The data containing stock and player information.
     */
    public void execute(AbstractStockOutputData abstractStockOutputData) {
        final StockPlayerState stockPlayerState = stockPlayerViewModel.getState();
        final StockState stockState = stockPlayerState.getStockState();
        stockState.setAllowBuy(abstractStockOutputData.isAllowBuy());
        stockState.setTicker(abstractStockOutputData.getTicker());
        stockState.setPrice(abstractStockOutputData.getPrice());
        stockState.setChange(abstractStockOutputData.getChange());
        stockPlayerState.setQuantity(abstractStockOutputData.getQuantity());
        System.out.println(abstractStockOutputData.getQuantity());
        stockPlayerState.setPlayer(abstractStockOutputData.getPlayer());
        stockPlayerViewModel.firePropertyChanged();

    }
}
