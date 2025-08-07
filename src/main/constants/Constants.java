package main.constants;

/**
 * This class is a placeholder for constants used throughout the application.
 */
public class Constants {
    // Toggle API usage
    public static final boolean USE_API = false;

    // Game Constants
    // Game JFrame related main.Constants.main.Constants
    public static final String GAME_TITLE = "Very Epic and Cool Monopoly Game";
    public static final int GAME_WIDTH = 1920;
    public static final int GAME_HEIGHT = 1080;
    public static final int START_X = 50;
    public static final int START_Y = 8;
    public static final int PORTRAIT_X_ADJ = 60;
    public static final int PORTRAIT_Y_ADJ = 200;
    public static final int PORTRAIT_PADDING = 30;
    public static final int BUTTON_PANEL_PADDING = 10;
    public static final int BUTTON_PANEL_VERTICAL_PADDING = 20;
    public static final int BUTTON_FONT_SIZE = 16;

    // GamePlay related constants
    public static final int MAX_ROUNDS = 20;
    public static final int PLACEHOLDER_RENT = 50;

    // Board JPanel Related Constants
    public static final int BOARD_SIZE = 780;
    public static final int BOARD_PANEL_WIDTH = 900;
    public static final int BOARD_PANEL_HEIGHT = 900;
    public static final int BOARD_SIDES = 4;

    // Stock Market and Stock Related
    public static final int NUM_STOCKS = 5;

    public static final String STOCK_NAME_FILE = "src/main/Resources/StockData/stock_names.json";
    public static final int STOCK_MARKET_WIDTH = 800;
    public static final int STOCK_MARKET_HEIGHT = 400;
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

    // Math
    public static final double PERCENTAGE_MULTIPLIER = 100.0;
    public static final int TILES_PER_SIDE_ADJ_3 = 3;

    // Bonus Money
    public static final float FINISH_LINE_BONUS = 200.0F;

    // Player Drawing
    public static final int PLAYER_SIZE = 25;

    // PlayerStatsView
    public static final int DIMENSION_DE_LA_PSV_H = 200;
    public static final int DIMENSION_DE_LA_PSV_W = 200;
    public static final int STATS_PANEL_WIDTH = 400;
    public static final int STATS_PANEL_LAYER = 3;

    // Character Constants
    public static final int CLERK_INIT_MONEY = 1200;
    public static final int STUDENT_INIT_MONEY = 1000;
    public static final int DEFAULT_INIT_MONEY = 1200;
    public static final int INHERITOR_INIT_MONEY = 1800;
    public static final int LANDLORD_INIT_MONEY = 800;
    public static final int POORMAN_INIT_MONEY = 200;

    public static final int CLERK_ADD_MONEY = 50;
}
