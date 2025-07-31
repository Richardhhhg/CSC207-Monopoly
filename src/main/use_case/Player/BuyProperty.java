package main.use_case.Player;

import main.entity.players.Player;
import main.entity.tiles.PropertyTile;

public class BuyProperty {
    public boolean execute(Player buyer, PropertyTile property) {
        if (property.isOwned()) return false;

        float price = property.getPrice();
        if (buyer.getMoney() < price) return false;
        buyer.deductMoney(price);
        property.setOwned(true, buyer);
        buyer.getProperties().add(property);
        return true;
    }
}
