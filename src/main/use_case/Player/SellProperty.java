package main.use_case.Player;

import main.entity.players.Player;
import main.entity.tiles.PropertyTile;

public class SellProperty {
    public boolean execute(Player seller, PropertyTile property) {
        if (property.getOwner() != seller) return false;

        float refund = property.getPrice();
        seller.addMoney(refund);
        seller.getProperties().remove(property);
        property.setOwned(false, null);
        return true;
    }
}
