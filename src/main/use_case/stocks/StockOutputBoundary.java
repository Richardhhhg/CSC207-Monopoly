package main.use_case.stocks;

public interface StockOutputBoundary {
    /**
     * Presents the stock information to the user.
     *
     * @param abstractStockOutputData The data containing stock and player information.
     */
    void execute(AbstractStockOutputData abstractStockOutputData);
}
