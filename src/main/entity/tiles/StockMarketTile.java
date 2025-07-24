package main.entity.tiles;

import main.data_access.StockMarket.StockInfoDataOutputObject;
import main.entity.Stock;
import main.use_case.Tile;
import main.view.StockMarketView;

import java.util.List;

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
    public void onLanding(main.use_case.Player player) {
        // TODO: This is a temporary implementation of the view just to test if it shows up
        StockMarketView.main(new String[0]);
    }
}
