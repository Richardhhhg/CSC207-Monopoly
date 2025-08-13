package main.use_case.tiles;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;
import main.entity.tiles.StockMarketTile;
import main.entity.tiles.Tile;
import main.use_case.tiles.property.PropertyPurchaseUseCase;
import main.use_case.tiles.property.RentPaymentUseCase;
import main.view.stock.StockMarketView;

public class OnLandingUseCase {
    private final PropertyPurchaseUseCase propertyPurchaseUseCase;
    private final RentPaymentUseCase rentPaymentUseCase;

    public OnLandingUseCase(PropertyPurchaseUseCase propertyPurchaseUseCase,
                           RentPaymentUseCase rentPaymentUseCase) {
        this.propertyPurchaseUseCase = propertyPurchaseUseCase;
        this.rentPaymentUseCase = rentPaymentUseCase;
    }

    public void execute(Player player, Tile tile) {
        if (tile instanceof PropertyTile) {
            PropertyTile property = (PropertyTile) tile;
            handlePropertyLanding(player, property);
        } else if (tile instanceof StockMarketTile) {
            handleStockMarketTileLanding(player);
        }
    }

    private void handlePropertyLanding(Player player, PropertyTile property) {
        if (!property.isOwned()) {
            // Property is unowned - trigger purchase flow
            propertyPurchaseUseCase.execute(player, property);
        } else if (player != property.getOwner()) {
            // Property is owned by someone else - calculate rent and delegate to RentPaymentUseCase
            float rent = property.calculateRent();

            // Delegate the entire rent payment process (including money transfer) to RentPaymentUseCase
            rentPaymentUseCase.execute(player, property.getOwner(), property, rent);
        }
        // If player owns the property, nothing happens
    }

    private void handleStockMarketTileLanding(Player player) {
        StockMarketView stockMarketView = new StockMarketView(player, true);
        stockMarketView.setVisible(true);
    }
}
