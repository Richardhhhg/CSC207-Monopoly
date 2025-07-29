package main.entity.tiles;

import main.use_case.Player;
import main.use_case.Tile;
import main.view.StockMarketView;

public class StockMarketTile extends Tile {
    public StockMarketTile() {
        super("Stock Market");
    }

    /**
     * When player lands on a stock market tile, the stock market for that player should be displayed
     *
     * @param player landing player
     */
    @Override
    public void onLanding(Player player) {
        // TODO: This is a temporary implementation of the view just to test if it shows up
        StockMarketView marketView = new StockMarketView(player);
        marketView.setVisible(true);
    }
}
