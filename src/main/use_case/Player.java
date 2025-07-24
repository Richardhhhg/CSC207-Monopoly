/*
TODO: IMPLEMENT THIS.
 */
package main.use_case;
import main.entity.Stock;
import main.entity.tiles.PropertyTile;

import javax.imageio.ImageIO;
import java.util.Map;
import java.util.HashMap;
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
    protected ArrayList<PropertyTile> properties;
    protected Map<Stock, Integer> stocks;
    protected Image portrait;
    private Color color;

    public Player(String name, int initialMoney, Color color) {
        this.name = name;
        this.money = initialMoney;
        this.position = 0;
        this.bankrupt = false;
        this.properties = new ArrayList<>();
        this.color = color;
        this.portrait = null;
        this.stocks = new HashMap<>();
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

    public List<PropertyTile> getProperties() {
        return properties;
    }

    public Map<Stock, Integer> getStocks() {
        return stocks;
    }

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

    public void setPosition(int newPosition) {
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
        for (PropertyTile propertyTile : this.properties) {
            propertyTile.setOwned(false, null);
        }
        this.properties.clear();
    }

    public void buyProperty(PropertyTile propertyTile) {
        float Price = propertyTile.getPrice();
        this.deductMoney(Price);
        this.properties.add(propertyTile);
        propertyTile.setOwned(true, this);

    }

    //TODO: Implement property selling methods
    public void sellProperty(PropertyTile propertyTile) {
        if (this.properties.contains(propertyTile)) {
            float refund = propertyTile.getPrice();
            this.addMoney(refund);
            this.properties.remove(propertyTile);
            propertyTile.setOwned(false, null);
        }
    }

    public abstract void buyStock(Stock stock, int quantity);
    public abstract void sellStock(Stock stock, int quantity);

    public int getStockQuantity(Stock stock) {
        return stocks.getOrDefault(stock, 0);
    }

    public void initializeStocks(List<Stock> stockList) {
        for (Stock stock : stockList) {
            stocks.put(stock, 0); // Initialize with 0 quantity
        }
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public abstract float adjustStockBuyPrice(float basePrice);
    public abstract float adjustStockSellPrice(float basePrice);
    public abstract float adjustRent(float baseRent);
    public abstract void applyTurnEffects(); // salary, tuition...depends on the character
}