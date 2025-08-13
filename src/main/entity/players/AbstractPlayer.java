package main.entity.players;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import main.entity.stocks.Stock;
import main.entity.tiles.PropertyTile;

public abstract class AbstractPlayer {
    private String name;
    private float money;
    private int position;
    private boolean bankrupt;
    private ArrayList<PropertyTile> properties;
    private Map<Stock, Integer> stocks;
    private Image portrait;
    private Color color;
    private boolean isNullPlayer;

    public AbstractPlayer(String name, float initialMoney, Color color) {
        this.name = name;
        this.money = initialMoney;
        this.position = 0;
        this.bankrupt = false;
        this.properties = new ArrayList<>();
        this.color = color;
        this.portrait = null;
        this.stocks = new HashMap<>();
    }

    public AbstractPlayer() {
        this.isNullPlayer = true;
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
            final InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
            if (is != null) {
                this.portrait = ImageIO.read(is);
            }
            else {
                System.err.println("Could not find portrait: " + resourcePath);
            }
        }
        catch (IOException exception) {
            System.err.println("Failed to load portrait: " + exception.getMessage());
        }
    }

    public void setPosition(int newPosition) {
        this.position = newPosition;
    }

    /**
     * Add money to the player's balance.
     *
     * @param amount The amount to add.
     */
    public void addMoney(float amount) {
        money += amount;
    }

    /**
     * Deduct money from the player's balance. If the balance goes to zero or below,
     *
     * @param amount The amount to deduct.
     */
    public void deductMoney(float amount) {
        money -= amount;
        if (money <= 0) {
            this.bankrupt = true;
            money = 0;
        }
    }

    /**
     * Buy a property tile and update ownership.
     *
     * @param propertyTile The property tile to buy.
     */
    public void buyProperty(PropertyTile propertyTile) {
        final float price = propertyTile.getPrice();
        this.deductMoney(price);
        this.properties.add(propertyTile);
        propertyTile.setOwned(true, this);

    }

    /**
     * Buy stocks and update the player's stock holdings.
     *
     * @param stock Stock to buy.
     * @param quantity Number of shares to buy.
     */
    public void buyStock(Stock stock, int quantity) {
        final double totalCost = stock.getCurrentPrice() * quantity;

        if (this.money >= totalCost) {
            this.deductMoney((float) totalCost);
            stocks.put(stock, stocks.getOrDefault(stock, 0) + quantity);
        }
    }

    /**
     * Sells stocks and updates the player's stock holdings.
     *
     * @param stock Stock to sell.
     * @param quantity Number of shares to sell.
     */
    public void sellStock(Stock stock, int quantity) {
        if (stocks.getOrDefault(stock, 0) >= quantity) {
            final double totalSale = stock.getCurrentPrice() * quantity;
            this.addMoney((float) totalSale);
            stocks.put(stock, stocks.get(stock) - quantity);
        }
    }

    /**
     * Get the quantity of a specific stock owned by the player.
     *
     * @param stock The stock to check.
     * @return The quantity of the stock owned.
     */
    public int getStockQuantity(Stock stock) {
        return stocks.getOrDefault(stock, 0);
    }

    /**
     * Initialize the player's stock holdings with a list of stocks, setting their
     * quantities to zero.
     *
     * @param stockList List of stocks to initialize.
     */
    public void initializeStocks(List<Stock> stockList) {
        for (Stock stock : stockList) {
            stocks.put(stock, 0);
        }
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Set the player's bankrupt status. If set to true, the player's money is set to
     * zero.
     *
     * @param bankrupt True if the player is bankrupt, false otherwise.
     */
    public void setBankrupt(boolean bankrupt) {
        this.bankrupt = bankrupt;
        if (bankrupt) {
            this.money = 0;
        }
    }

    /**
     * Set the player's properties.
     *
     * @param properties List of properties to set.
     */
    public void setProperties(List<PropertyTile> properties) {
        this.properties.clear();
        if (properties != null) {
            this.properties.addAll(properties);
        }
    }

    public Color getColor() {
        return color;
    }

    public boolean isNullPlayer() {
        return this.isNullPlayer;
    }
}
