package main.entity.tiles;

import main.entity.players.AbstractPlayer;
import main.entity.players.RentModifier;

/**
 * A purchasable board tile that can collect rent.
 */
public class PropertyTile extends AbstractTile {
    private final int price;
    private final float rent;
    // null if not owned
    private AbstractPlayer owner;

    /**
     * Creates a new PropertyTile with specified name, price, and rent.
     *
     * @param name  tile name
     * @param price purchase price
     * @param rent  base rent amount
     */
    public PropertyTile(String name, int price, float rent) {
        super(name);
        this.price = price;
        this.rent = rent;
    }

    /**
     * Gets the purchase price of this property.
     *
     * @return purchase price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Gets the base rent amount before any adjustments.
     *
     * @return base rent (before adjustment)
     */
    public float getRent() {
        return rent;
    }

    /**
     * Calculate the actual rent to be paid, including any modifiers.
     *
     * @return the calculated rent amount
     */
    public float calculateRent() {
        float finalRent = rent;
        if (owner instanceof RentModifier) {
            finalRent = ((RentModifier) owner).adjustRent(rent);
        }
        return finalRent;
    }

    /**
     * Checks if this property is owned by someone.
     *
     * @return true if someone owns this property
     */
    public boolean isOwned() {
        return owner != null;
    }

    /**
     * Gets the owner of this property.
     *
     * @return the owning player, or null if unowned
     */
    public AbstractPlayer getOwner() {
        return owner;
    }

    /**
     * Business logic for purchasing this property.
     *
     * @param abstractPlayer the player attempting to purchase
     * @return true if purchase was successful, false otherwise
     */
    public boolean attemptPurchase(AbstractPlayer abstractPlayer) {
        final boolean canPurchase = !isOwned() && abstractPlayer.getMoney() >= price;
        if (canPurchase) {
            abstractPlayer.buyProperty(this);
            this.owner = abstractPlayer;
        }
        return canPurchase;
    }

    /**
     * Sets or clears ownership.
     *
     * @param owned ignored; ownership is determined by non-null owner
     * @param newOwner the new owner, or null to clear
     */
    public void setOwned(boolean owned, AbstractPlayer newOwner) {
        this.owner = newOwner;
    }
}
