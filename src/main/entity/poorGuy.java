package main.entity;

import main.use_case.Player;

import java.awt.*;

public class poorGuy extends Player {
    private static final int POORGUY_INIT_MONEY = 20;
    public poorGuy(String name, Color color) {
        super(name, POORGUY_INIT_MONEY, color);
        this.loadPortrait("main/Resources/poorguy.png");
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
        System.out.println("Oh common Man!");
        this.deductMoney(20);
    }
}
