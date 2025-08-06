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

    // GamePlay related constants
    public static final int MAX_ROUNDS = 20;
    public static final int PLACEHOLDER_RENT = 50;

    // Board JPanel Related main.Constants.main.Constants
    public static final int BOARD_SIZE = 780;
    public static final int BOARD_PANEL_WIDTH = 900;
    public static final int BOARD_PANEL_HEIGHT = 900;

    // Stock Market and Stock Related
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

    // Bonus Money
    public static final float FINISH_LINE_BONUS = 200.0F;

    // Player Drawing
    public static final int PLAYER_SIZE = 25;

    // PlayerStatsView
    public static final int DIMENSION_DE_LA_PSV_H = 200;
    public static final int DIMENSION_DE_LA_PSV_W = 200;

    // Character Constants
    public static final int CLERK_INIT_MONEY = 1200;
    public static final int STUDENT_INIT_MONEY = 1000;
    public static final int DEFAULT_INIT_MONEY = 1200;
    public static final int INHERITOR_INIT_MONEY = 1800;
    public static final int LANDLORD_INIT_MONEY = 800;
    public static final int POORMAN_INIT_MONEY = 200;

    public static final int CLERK_ADD_MONEY = 50;

    //Portrait Path
    public static final String CLERK_POR = "main/Resources/clerk.jpg";
    public static final String CS_POR = "main/Resources/Computer-nerd.jpg";
    public static final String LL_POR = "main/Resources/landlord.png";
    public static final String INH_POR = "main/Resources/inheritor.jpg";
    public static final String PP_POR = "main/Resources/poorman.png";
    public static final String NP_POR= "main/Resources/default portrait.png";

    //CHARSELSCR CONST
    public static final int MAX_NP_BAR = 3;

}