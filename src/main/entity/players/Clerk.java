package main.entity.players;

import java.awt.Color;

import main.constants.Constants;

public class Clerk extends AbstractPlayer implements ApplyAfterEffects {
    public Clerk(String name, Color color) {
        super(name, Constants.CLERK_INIT_MONEY, color);
        this.loadPortrait(Constants.CLERK_POR);
    }

    /**
     * Apply turn effect. Clerk Earns 50$ per round
     */
    @Override
    public void applyTurnEffects() {
        this.addMoney(Constants.CLERK_ADD_MONEY);
        System.out.println("Just another day");
    }
}
