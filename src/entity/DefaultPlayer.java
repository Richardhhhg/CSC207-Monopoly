package entity;

import use_case.Player;

public class DefaultPlayer extends Player {
    private String name;

    public DefaultPlayer(String name, int initialMoney) {
        super(name, initialMoney);
        this.loadPortrait("Resources/default portrait.png");
    }

    public String  getName() {
        return this.name;
    }

    public float getMoney() {
        return this.money;
    }

    public int getPosition() {
        return this.position;
    }

    /**
     * @param basePrice
     * @return
     */
    @Override
    public float adjustStockBuyPrice(float basePrice) {
        return basePrice;
    }

    @Override
    public float adjustStockSellPrice(float basePrice) {
        return basePrice;
    }

    @Override
    public float adjustRent(float baseRent) {
        return baseRent;
    }

    @Override
    public void applyTurnEffects() {
        System.out.println("nope, normie");
    }
}
