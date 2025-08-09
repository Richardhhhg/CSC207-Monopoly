package main.interface_adapter.StockMarket;

/**
 * Viewmodel for Individual Stock held by a player.
 */
public class StockState {
    private boolean allowBuy;
    private String ticker;
    private float price;
    private float change;
    private int quantity;

    public boolean isAllowBuy() {
        return allowBuy;
    }

    public void setAllowBuy(boolean allowBuy) {
        this.allowBuy = allowBuy;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getChange() {
        return change;
    }

    public void setChange(float change) {
        this.change = change;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
