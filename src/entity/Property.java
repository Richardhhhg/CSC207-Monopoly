package entity;

import use_case.Tile;
import use_case.Player;

/*
TODO: CLEAN THIS UP
 */
public class Property extends Tile {
    private int price;
    private int rent;
    private boolean owned;
    private Player owner;

    public Property(String name, int price, int rent) {
        super(name);
        this.price = price;
        this.rent = rent;
        this.owned = false;
    }

    public int getPrice() {
        return price;
    }

    public int getRent() {
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
        } else {
            // Logic for paying rent to the owner
            // e.g., deducting rent from the current player and giving it to the owner
        }
    }
}