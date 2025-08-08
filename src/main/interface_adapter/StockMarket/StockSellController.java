package main.interface_adapter.StockMarket;

import main.entity.Stocks.Stock;
import main.entity.players.Player;
import main.use_case.Stocks.SellStockInputBoundary;
import main.use_case.Stocks.SellStockInputData;

/**
 * Controller for selling stocks.
 * Updated to use input boundary interface and DTOs while maintaining backward compatibility.
 */
public class StockSellController {
    private final SellStockInputBoundary sellStockUseCase;

    // New constructor using dependency injection
    public StockSellController(SellStockInputBoundary sellStockUseCase) {
        this.sellStockUseCase = sellStockUseCase;
    }

    // Backward compatibility constructor
    public StockSellController() {
        this.sellStockUseCase = null;
    }

    // New method using clean architecture
    public void execute(String playerId, String stockTicker, double stockPrice, int quantity) {
        if (sellStockUseCase != null) {
            SellStockInputData inputData = new SellStockInputData(playerId, stockTicker, stockPrice, quantity);
            sellStockUseCase.execute(inputData);
        }
    }

    // Backward compatibility method for existing code
    public void execute(Player player, Stock stock, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (player.getStockQuantity(stock) < quantity) {
            throw new IllegalArgumentException("Insufficient stocks to sell");
        }

        player.sellStock(stock, quantity);
    }
}
