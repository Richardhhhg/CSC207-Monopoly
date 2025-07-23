package main.entity.tiles;

import main.use_case.Tile;
import main.use_case.Player;

/*
 * A purchasable board tile that can collect rent.
 * */
public class Property extends Tile {
    private final float price;
    private final float rent;
    private Player owner; //null if not owned

    /**
     * @param name  tile name
     * @param price purchase price
     * @param rent  base rent amount
     */
    public Property(String name, float price, float rent) {
        super(name);
        this.price = price;
        this.rent = rent;
    }

    /**
     * @return purchase price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @return base rent (before adjustment)
     */
    public float getRent() {
        return rent;
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
     * When a player lands here:
     * 1) If unowned, attempt to buy via Player.buyProperty(...)
     * 2) If owned by someone else, charge rent.
     */
    @Override
    public void onLanding(Player p) {
        if (!isOwned()) {
            p.buyProperty(this);
            return;
        }
        if (p != owner) {
            float finalRent = owner.adjustRent(rent);
            p.deductMoney(finalRent);
            owner.addMoney(finalRent);
        }
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
