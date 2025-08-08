package main.interface_adapter.StockMarket;

import main.use_case.Stocks.SellStockOutputBoundary;
import main.use_case.Stocks.SellStockOutputData;

/**
 * Presenter implementation for sell stock use case output.
 * Implements the output boundary interface and handles presentation logic.
 */
public class SellStockPresenter implements SellStockOutputBoundary {
    private String successMessage;
    private String errorMessage;
    private boolean success;

    @Override
    public void presentSuccess(SellStockOutputData outputData) {
        this.success = true;
        this.successMessage = String.format("Successfully sold %d shares of %s for $%.2f. New money: $%.2f",
            outputData.getQuantity(),
            outputData.getStockTicker(),
            outputData.getTotalSale(),
            outputData.getNewMoney());
        this.errorMessage = null;
    }

    @Override
    public void presentError(String error) {
        this.success = false;
        this.errorMessage = error;
        this.successMessage = null;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}