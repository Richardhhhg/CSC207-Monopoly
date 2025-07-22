package main.entity;

import main.use_case.Player;

import java.awt.*;

public class collegeStudent extends Player {
    private static final int STUDENT_INIT_MONEY = 1000;
    public collegeStudent(String name, Color color) {
        super(name, STUDENT_INIT_MONEY,color);
        this.loadPortrait("main/Resources/Computer-nerd.jpg");
    }

    /**
     * @param basePrice
     * @return
     */
    @Override
    public float adjustStockBuyPrice(float basePrice) {
        return basePrice * 0.90f;
    }

    /**
     * @param basePrice
     * @return
     */
    @Override
    public float adjustStockSellPrice(float basePrice) {
        return basePrice * 1.3f;
    }

    /**
     * @param baseRent
     * @return
     */
    @Override
    public float adjustRent(float baseRent) {
        return baseRent;
    }

    /**
     * College Student has to pay his school tuition every turn.
     */
    @Override
    public void applyTurnEffects() {
        this.deductMoney(100);
        System.out.println("ah man, the tuition goes up again!");
    }
}
