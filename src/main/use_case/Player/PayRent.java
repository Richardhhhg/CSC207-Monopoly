package main.use_case.Player;

import main.entity.players.Player;
import main.entity.players.rentModifier;
import main.entity.tiles.PropertyTile;

public class PayRent{
    public float execute(Player payer, PropertyTile property) {
        Player owner = property.getOwner();
        if(owner == null || owner == payer) {
            return 0f;
        }

        float baseRent = property.getRent();
        float rent = baseRent;

        if (owner instanceof rentModifier) {
            rent = ((rentModifier) owner).adjustRent(baseRent);
        }
        payer.deductMoney(rent);

        if (payer.isBankrupt()) {
            new DeclareBankruptcy().execute(payer);
        }

        owner.addMoney(rent);
        return rent;
    }

}
