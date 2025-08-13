package main.use_case.tiles;

import main.entity.players.Player;
import main.entity.tiles.AbstractTile;
import main.entity.tiles.PropertyTile;
import main.entity.tiles.StockMarketTile;
import main.use_case.tiles.property.PropertyPurchaseUseCase;
import main.use_case.tiles.property.RentPaymentUseCase;
import main.view.stock.StockMarketView;

/**
 * Use case class for handling actions when landing on tiles.
 */
public class OnLandingUseCase {
    private final PropertyPurchaseUseCase propertyPurchaseUseCase;
    private final RentPaymentUseCase rentPaymentUseCase;

    /**
     * Constructor for OnLandingUseCase.
     *
     * @param propertyPurchaseUseCase the use case for property purchase
     * @param rentPaymentUseCase      the use case for rent payment
     */
    public OnLandingUseCase(PropertyPurchaseUseCase propertyPurchaseUseCase,
                           RentPaymentUseCase rentPaymentUseCase) {
        this.propertyPurchaseUseCase = propertyPurchaseUseCase;
        this.rentPaymentUseCase = rentPaymentUseCase;
    }

    /**
     * Executes the appropriate action when a player lands on a tile.
     *
     * @param player the player who landed on the tile
     * @param tile   the tile the player landed on
     */
    public void execute(Player player, AbstractTile tile) {
        if (tile instanceof PropertyTile) {
            final PropertyTile property = (PropertyTile) tile;
            handlePropertyLanding(player, property);
        }
        else if (tile instanceof StockMarketTile) {
            handleStockMarketTileLanding(player);
        }
    }

    private void handlePropertyLanding(Player player, PropertyTile property) {
        if (!property.isOwned()) {
            // Property is unowned - trigger purchase flow
            propertyPurchaseUseCase.execute(player, property);
        }
        else if (player != property.getOwner()) {
            // Property is owned by someone else - calculate rent and delegate to RentPaymentUseCase
            final float rent = property.calculateRent();

            // Delegate the entire rent payment process (including money transfer) to RentPaymentUseCase
            rentPaymentUseCase.execute(player, property.getOwner(), property, rent);
        }
        // If player owns the property, nothing happens
    }

    private void handleStockMarketTileLanding(Player player) {
        final StockMarketView stockMarketView = new StockMarketView(player, true);
        stockMarketView.setVisible(true);
    }
}
