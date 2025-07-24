package main.entity;

import main.use_case.Player;

import java.awt.*;

public class PoorMan extends Player {
    private static final int POORMAN_INIT_MONEY = 20;
    public PoorMan(String name, Color color) {
        super(name, POORMAN_INIT_MONEY, color);
        this.loadPortrait("main/Resources/poorman.png");
    }

    @Override
    public float adjustStockBuyPrice(float basePrice) {
        return 0;
    }

    @Override
    public float adjustStockSellPrice(float basePrice) {
        return 0;
    }

    @Override
    public float adjustRent(float baseRent) {
        return 0;
    }

    @Override
    public void applyTurnEffects() {
        System.out.println("spare a dollar for a poor fellah?");
        this.deductMoney(20);

    }
}
