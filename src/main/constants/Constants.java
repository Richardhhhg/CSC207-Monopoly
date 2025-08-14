package main.constants;

import java.awt.Color;

/**
 * This class is a placeholder for constants used throughout the application.
 */
public class Constants {
    public static final boolean USE_STOCK_API = true;
    public static final int NUM_STOCKS = 5;

    // Game Constants
    public static final int SMALL_BOARD_SIZE = 20;
    public static final int MEDIUM_BOARD_SIZE = 24;
    public static final int LARGE_BOARD_SIZE = 28;

    // Game JFrame related main.Constants.main.Constants
    public static final String GAME_TITLE = "Very Epic and Cool Monopoly Game";
    public static final int GAME_WIDTH = 1920;
    public static final int GAME_HEIGHT = 1080;
    public static final int START_X = 50;
    public static final int START_Y = 8;

    // GamePlay related constants
    public static final int MAX_ROUNDS = 10;
    public static final float RENT_MULTIPLIER = 0.25f;
    public static final int PROPERTY_BASE_PRICE = 80;
    public static final int PROPERTY_PRICE_INCREMENT = 25;

    // Board JPanel Related main.Constants.main.Constants
    public static final int BOARD_SIZE = 780;
    public static final int BOARD_PANEL_WIDTH = 900;
    public static final int BOARD_PANEL_HEIGHT = 900;

    // Stock Market and Stock Related
    // Gameplay related
    public static final double DEFAULT_STOCK_PRICE = 100.0;
    public static final double DEFAULT_STOCK_RETURN = 10.0;
    public static final double DEFAULT_STOCK_STD_DEV = 30.0;

    // View Related
    public static final String STOCK_NAME_FILE = "src/main/Resources/StockData/stock_names.json";
    public static final int STOCK_MARKET_WIDTH = 800;
    public static final int STOCK_MARKET_HEIGHT = 400;
    public static final int STOCK_MKT_PADDING = 10;
    public static final int STOCK_MKT_COLUMNS = 7;
    public static final Color STOCK_MKT_COLOR = new Color(29, 145, 56, 128);
    public static final float STOCK_MKT_TILE_FONT_SIZE = 14.0f;

    public static final int STOCK_WIDTH = 1600;
    public static final int STOCK_HEIGHT = 200;
    public static final int STOCK_VIEW_ROWS = 1;
    public static final int STOCK_VIEW_COLUMNS = 5;
    public static final int STOCK_VIEW_PADDING_H = 5;
    public static final int STOCK_VIEW_PADDING_V = 5;
    public static final int STOCK_QUANTITY_WIDTH = 5;

    // API Constants
    public static final String STOCK_API_URL = "https://www.alphavantage.co/query";
    public static final int YEARS_OF_DATA = 5;

    // Dice Constants
    public static final int DICE_SIDES = 6;
    public static final int DICE_ICON_COUNT = DICE_SIDES + 1;
    public static final int DICE_FRAME_LIMIT = 9;
    public static final int DICE_DELAY_MS = 100;
    public static final int DICE_MIN_SUM = 2;
    public static final int DICE_DEFAULT_FACE = 1;

    // Math
    public static final double PERCENTAGE_MULTIPLIER = 100.0;

    // Bonus Money
    public static final float FINISH_LINE_BONUS = 200.0F;

    // Player Drawing
    public static final int PLAYER_SIZE = 25;
    public static final int PORTRAIT_ADJUST_HEIGHT = 30;
    public static final int PORTRAIT_LABEL_FONT_SIZE = 14;
    public static final int PLAYER_X = 5;
    public static final int PLAYER_Y = 5;
    public static final int PLAYER_WIDTH = 15;
    public static final int PLAYER_HEIGHT = 15;

    // PlayerStatsView
    public static final int STATS_WIDTH = 600;
    public static final int STATS_LAYER = 3;

    // Character Constants
    public static final int CLERK_INIT_MONEY = 800;
    public static final int STUDENT_INIT_MONEY = 800;
    public static final int DEFAULT_INIT_MONEY = 1200;
    public static final int INHERITOR_INIT_MONEY = 1200;
    public static final int LANDLORD_INIT_MONEY = 1000;
    public static final int POORMAN_INIT_MONEY = 200;

    public static final int CLERK_ADD_MONEY = 20;
    public static final int POORMAN_TURN_EFFECTS_COST = 20;
    public static final int COLLEGE_STUDENT_TUITION = 20;
    public static final float COLLEGE_STUDENT_STOCK_PRICE = 0.85f;
    public static final float COLLEGE_STUDENT_STOCK_SELL_PRICE = 1.1f;
    public static final float INHERITOR_STOCK_BUY_PRICE = 1.1f;
    public static final float INHERITOR_STOCK_SELL_PRICE = 0.9f;
    public static final float LANDLORD_STOCK_BUY_PRICE = 1.15f;
    public static final float LANDLORD_STOCK_SELL_PRICE = 0.85f;
    public static final float LANDLORD_RENT_MULTIPLIER = 1.8f;

    // Portrait Path
    public static final String CLERK_POR = "CharacterPortrait/clerk.jpg";
    public static final String CS_POR = "CharacterPortrait/Computer-nerd.jpg";
    public static final String LL_POR = "CharacterPortrait/landlord.png";
    public static final String INH_POR = "CharacterPortrait/inheritor.jpg";
    public static final String PP_POR = "CharacterPortrait/poorman.png";
    public static final String NP_POR = "CharacterPortrait/default portrait.png";

    // CHARSELSCR CONST
    public static final int MAX_NP_BAR = 2;

    // UI Start-Screen dimensions
    public static final int START_SCREEN_WIDTH = 600;
    public static final int START_SCREEN_HEIGHT = 400;

    // Shared font settings
    public static final String UI_FONT_FAMILY = "Arial";
    public static final int TITLE_FONT_SIZE = 30;
    public static final int START_BUTTON_FONT_SIZE = 20;
    public static final int RULES_BUTTON_FONT_SIZE = 16;
    public static final String TWO_DECIMALS = "%.2f";

    // End-screen constants
    // Window sizes
    public static final int END_SCREEN_WIDTH = 800;
    public static final int END_SCREEN_HEIGHT = 600;

    // Scroll area sizes
    public static final int END_SCROLL_WIDTH = 750;
    public static final int END_SCROLL_HEIGHT = 400;

    // Common spacing/padding
    public static final int PAD_LARGE = 20;
    public static final int PAD_MEDIUM = 15;
    public static final int GAP_SMALL = 5;
    public static final int GAP_MEDIUM = 10;
    public static final int GAP_LARGE = 15;

    // Fonts
    public static final int WINNER_FONT_SIZE = 24;
    public static final int GAME_OVER_FONT_SIZE = 36;
    public static final int REASON_FONT_SIZE = 18;
    public static final int ROUNDS_FONT_SIZE = 16;
    public static final int MONEY_LABEL_FONT_SIZE = 14;
    public static final int STATUS_FONT_SIZE = 14;
    public static final int NET_WORTH_FONT_SIZE = 16;
    public static final int PROPERTY_LABEL_FONT_SIZE = 12;
    public static final int RANK_FONT_SIZE = MONEY_LABEL_FONT_SIZE + 6;

    // Colors
    public static final Color BG_LIGHT = new Color(240, 240, 240);
    public static final Color TITLE_BG = new Color(220, 220, 220);
    public static final Color WINNER_GREEN = new Color(0, 150, 0);
    public static final Color NET_GREEN = new Color(0, 120, 0);
    public static final Color TITLE_RED = new Color(150, 0, 0);

    // Component sizes
    public static final int PORTRAIT_SIZE_PX = 80;
    public static final int BORDER_THICKNESS = 3;
    public static final int PROPERTIES_PANEL_WIDTH = 200;
    public static final int PROPERTIES_PANEL_HEIGHT = 120;

    // Strings
    public static final String CURRENCY_PREFIX = "$";
}
