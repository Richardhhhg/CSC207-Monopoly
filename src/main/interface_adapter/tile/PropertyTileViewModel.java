package main.interface_adapter.tile;

import java.awt.*;

public class PropertyTileViewModel extends TileViewModel {
    private int price;
    private String owner;
    private float rent;
    private Color color;

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