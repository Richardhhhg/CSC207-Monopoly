package main.interface_adapter.StockMarket;

/**
 * Data Transfer Object for player stock information in the interface adapter layer.
 * This replaces direct dependencies on Player entities.
 */
public class PlayerStockData {
    private final String playerId;
    private final double money;
    private final int stockQuantity;

    public PlayerStockData(String playerId, double money, int stockQuantity) {
        this.playerId = playerId;
        this.money = money;
        this.stockQuantity = stockQuantity;
    }

    public String getPlayerId() {
        return playerId;
    }

    public double getMoney() {
        return money;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
}