package main.entity.players;

import java.awt.*;

import static main.constants.constants.CLERK_ADD_MONEY;
import static main.constants.constants.CLERK_INIT_MONEY;

public class clerk extends Player implements applyAfterEffects {
    public clerk(String name, Color color) {
        super(name, CLERK_INIT_MONEY, color);
        this.loadPortrait("main/Resources/clerk.jpg");
    }

    /**
     *
     * apply turn effect
     */
    @Override
    public void applyTurnEffects() {
        this.addMoney(CLERK_ADD_MONEY);
        System.out.println("Just another day");
    }
}
