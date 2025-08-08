package main.interface_adapter.StockMarket;

import main.entity.Stocks.Stock;
import main.entity.players.Player;
import main.use_case.Stocks.BuyStockInputBoundary;
import main.use_case.Stocks.BuyStockInputData;

/**
 * Controller for buying stocks.
 * Updated to use input boundary interface and DTOs while maintaining backward compatibility.
 */
public class StockBuyController {
    private final BuyStockInputBoundary buyStockUseCase;

    // New constructor using dependency injection
    public StockBuyController(BuyStockInputBoundary buyStockUseCase) {
        this.buyStockUseCase = buyStockUseCase;
    }

    // Backward compatibility constructor
    public StockBuyController() {
        this.buyStockUseCase = null;
    }

    // New method using clean architecture
    public void execute(String playerId, String stockTicker, double stockPrice, int quantity) {
        if (buyStockUseCase != null) {
            BuyStockInputData inputData = new BuyStockInputData(playerId, stockTicker, stockPrice, quantity);
            buyStockUseCase.execute(inputData);
        }
    }

    // Backward compatibility method for existing code
    public void execute(Player player, Stock stock, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (player.getMoney() < stock.getCurrentPrice() * quantity) {
            throw new IllegalArgumentException("Insufficient funds to buy stocks");
        }

        player.buyStock(stock, quantity);
    }
}
