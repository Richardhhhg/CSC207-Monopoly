package entity;

import use_case.Tile;
import use_case.Player;

/*
TODO: CLEAN THIS UP
 */
public class Property extends Tile {
    private float price;
    private float rent;
    private boolean owned;
    private Player owner;

    public Property(String name, float price, float rent) {
        super(name);
        this.price = price;
        this.rent = rent;
        this.owned = false;
    }

    public float getPrice() {
        return price;
    }

    public float getRent() {
        return rent;
    }

    public boolean isOwned() {
        return owned;
    }

    public Player getOwner() {
        return owner;
    }

    public String getName() {
        return this.name;
    }

    public void setOwned(boolean owned, Player owner) {
        this.owned = owned;
        this.owner = owner;
    }

    @Override
    public void event(Player player) {
        // TODO: IMPLEMENT THIS
        if (!owned) {
            // Logic for purchasing the property
            // e.g., deducting money from the player and marking the property as owned
            player.buyProperty(this);
        } else {
            // Logic for paying rent to the owner
            // e.g., deducting rent from the current player and giving it to the owner
            float finalRent = owner.adjustRent(this.rent);  // landlord can charge more
            player.deductMoney(finalRent);
            owner.addMoney(finalRent);
            System.out.println(player.getName() + " pays $" + finalRent + " rent to " + owner.getName());
        }
    }
}