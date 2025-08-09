package main.use_case.Stocks;

public interface StockOutputBoundary {
    /**
     * Presents the stock information to the user.
     *
     * @param stockOutputData The data containing stock and player information.
     */
    void execute(StockOutputData stockOutputData);
}
