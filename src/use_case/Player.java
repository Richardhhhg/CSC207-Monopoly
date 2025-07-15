/*
TODO: IMPLEMENT THIS
 */
package use_case;
import entity.Property;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected String name;
    protected int money;
    protected int position;
    protected boolean bankrupt;
    protected List<Property> properties;
    //protected List<Stock> stocks;
    protected Image portrait;

    public Player(String name, int initialMoney) {
        this.name = name;
        this.money = initialMoney;
        this.position = 0;
        this.bankrupt = false;
        this.properties = new ArrayList<>();
        //this.stocks = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public float getMoney() {
        return this.money;
    }

    public int getPosition() {
        return this.position;
    }

    public boolean isBankrupt() {
        return bankrupt;
    }

    public List<Property> getProperties() {
        return properties;
    }

    //public List<Stock> getStocks() {
        //return stocks;
    //}

    public Image getPortrait() {
        return portrait;
    }

    protected void loadPortrait(String resourcePath) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
            if (is != null) {
                this.portrait = ImageIO.read(is);
            } else {
                System.err.println("Could not find portrait: " + resourcePath);
            }
        } catch (IOException e) {
            System.err.println("Failed to load portrait: " + e.getMessage());
        }
    }

    public void moveTo(int newPosition) {
        this.position = newPosition;
    }

    public void addMoney(float amount) {
        money += amount;
    }

    public void deductMoney(float amount) {
        money -= amount;
        if (money <= 0) {
            this.bankrupt = true;
            bankrupcyReckoning();
            money = 0;
        }
    }

    public void bankrupcyReckoning(){
        for (Property property : this.properties) {
            property.setOwned(false, null);
        }
        this.properties.clear();
    }

    public void buyProperty(Property property) {
        float Price = property.getPrice();
        if (!property.isOwned() && this.money >= Price) {
            this.deductMoney(Price);
            this.properties.add(property);
            property.setOwned(true, this);
        }
    }

    public void sellProperty(Property property) {
        if (this.properties.contains(property)) {
            float refund = property.getPrice();
            this.addMoney(refund);
            this.properties.remove(property);
            property.setOwned(false, null);
        }
    }

    public abstract float adjustStockBuyPrice(float basePrice);
    public abstract float adjustStockSellPrice(float basePrice);
    public abstract float adjustRent(float baseRent);
    public abstract void applyTurnEffects(); // salary, tuition...depends on the character
}