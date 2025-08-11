package main.entity.players;

import static main.Constants.Constants.*;

import java.awt.*;

public class Clerk extends Player implements applyAfterEffects {
    public Clerk(String name, Color color) {
        super(name, CLERK_INIT_MONEY, color);
        this.loadPortrait(CLERK_POR);
    }

    /**
     * Apply turn effect. Clerk Earns 50$ per round
     */
    @Override
    public void applyTurnEffects() {
        this.addMoney(CLERK_ADD_MONEY);
        System.out.println("Just another day");
    }
}
