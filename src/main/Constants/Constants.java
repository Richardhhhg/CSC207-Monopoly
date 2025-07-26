package main.Constants;

import java.awt.*;

/**
 * This class is a placeholder for constants used throughout the application.
 */
public class Constants {
    // Game Constants
    // Game JFrame related main.Constants.main.Constants
    public static final String GAME_TITLE = "Very Epic and Cool Monopoly Game";
    public static final int GAME_WIDTH = 1920;
    public static final int GAME_HEIGHT = 1080;

    // Game entity related constants
    public static final int MAX_ROUNDS = 20;

    // Board JPanel Related main.Constants.main.Constants
    public static final int BOARD_SIZE = 780;
    public static final int BOARD_PANEL_WIDTH = 900;
    public static final int BOARD_PANEL_HEIGHT = 900;

    // Stock Market and Stock Related
    public static final String STOCK_NAME_FILE = "src/main/Resources/StockData/stock_names.json";
    public static final int STOCK_MARKET_WIDTH = 800;
    public static final int STOCK_MARKET_HEIGHT = 400;
    public static final int STARTER_PCT_CHANGE = 0;
    public static final int STARTER_QUANTITY = 0;
    public static final int STOCK_MKT_PADDING = 10;

    public static final int STOCK_WIDTH = 1600;
    public static final int STOCK_HEIGHT = 200;
    public static final int STOCK_VIEW_ROWS = 1;
    public static final int STOCK_VIEW_COLUMNS = 5;
    public static final int STOCK_VIEW_PADDING_H = 5;
    public static final int STOCK_VIEW_PADDING_V = 5;

    // API Constants
    public static final String STOCK_API_URL = "https://www.alphavantage.co/query";
    public static final int YEARS_OF_DATA = 5;
    public static final int API_RATE_LIMIT_DELAY_MS = 12000;

    // Math
    public static final double PERCENTAGE_MULTIPLIER = 100.0;

    // Bonus Money
    public static final float FINISH_LINE_BONUS = 200.0F;

    // PlayerStatesView
    public static final int DIMENSION_DE_LA_PSV_H = 200;
    public static final int DIMENSION_DE_LA_PSV_W = 200;
}