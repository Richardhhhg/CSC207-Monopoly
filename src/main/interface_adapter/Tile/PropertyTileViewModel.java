package main.interface_adapter.Tile;

import java.awt.*;

public class PropertyTileViewModel extends TileViewModel {
    private final int price;
    private final String owner;
    private final float rent;
    private final Color color;

    public PropertyTileViewModel(String name, int price, String owner, float rent, Color color) {
        super(name);
        this.owner = owner;
        this.price = price;
        this.rent = rent;
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public String getOwner() {
        return owner;
    }

    public float getRent() {
        return rent;
    }

    public boolean isOwned() {
        return owner != null && !owner.isEmpty();
    }

    public Color getOwnerColor() {
        return isOwned() ? color : Color.WHITE;
    }
}