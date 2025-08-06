package main.entity.tiles;

import main.entity.players.rentModifier;
import main.entity.players.Player;

/*
 * A purchasable board tile that can collect rent.
 * */
public class PropertyTile extends Tile {
    private final int price;
    private final float rent;
    private Player owner; //null if not owned

    /**
     * @param name  tile name
     * @param price purchase price
     * @param rent  base rent amount
     */
    public PropertyTile(String name, int price, int rent) {
        super(name);
        this.price = price;
        this.rent = rent;
    }

    /**
     * @return purchase price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @return base rent (before adjustment)
     */
    public float getRent() {
        return rent;
    }

    /**
     * Calculate the actual rent to be paid, including any modifiers
     */
    public float calculateRent() {
        float finalRent = rent;
        if (owner instanceof rentModifier) {
            finalRent = ((rentModifier) owner).adjustRent(rent);
        }
        return finalRent;
    }

    /**
     * @return true if someone owns this property
     */
    public boolean isOwned() {
        return owner != null;
    }

    /**
     * @return the owning player, or null if unowned
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Business logic for purchasing this property
     */
    public boolean attemptPurchase(Player player) {
        if (isOwned() || player.getMoney() < price) {
            return false;
        }

        player.buyProperty(this);
        this.owner = player;
        return true;
    }

    /**
     * Sets or clears ownership.
     *
     * @param owned ignored; ownership is determined by non-null owner
     * @param owner the new owner, or null to clear
     */
    public void setOwned(boolean owned, Player owner) {
        this.owner = owner;
    }
}
