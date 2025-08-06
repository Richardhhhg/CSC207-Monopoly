package main.entity.players;


import java.awt.*;

import static main.Constants.Constants.*;

public class Clerk extends Player implements applyAfterEffects {
    public Clerk(String name, Color color) {
        super(name, CLERK_INIT_MONEY, color);
        this.loadPortrait(CLERK_POR);
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
