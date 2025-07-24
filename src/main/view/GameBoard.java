package main.view;

import main.data_access.StockMarket.StockInfoDataOutputObject;
import main.entity.*;
import main.entity.tiles.PropertyTile;
import main.use_case.Player;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;

import static main.Constants.Constants.FINISH_LINE_BONUS;

/**
 * GameBoard manages the game state and logic, separate from UI concerns.
 */
public class GameBoard {
    private static final int PLACEHOLDER_RENT = 50;
    private static final int PLAYER_COUNT = 4;
    private static final Color[] PLAYER_COLORS = {Color.RED, Color.CYAN, Color.GREEN, Color.ORANGE};
    private static final String[] PLAYER_NAMES = {"Player 1", "Player 2", "Player 3", "Player 4"};
    private static final int MAX_ROUNDS = 20;
    private static final int TURNS_PER_ROUND = 4; // 4 players per round

    private ArrayList<PropertyTile> properties;
    private List<Player> players;
    private int currentPlayerIndex = 0;
    private int tileCount;
    private int totalTurns = 0;
    private boolean gameEnded = false;
    private String gameEndReason = "";

    public GameBoard() {
        initializeGame();
    }

    /**
     * Initializes Properties and Players
     */
    public void initializeGame() {
        // Initialize properties
        properties = new ArrayList<>();
        String[] propertyNames = {
                "GO", "Mediterranean Ave", "Baltic Ave", "Reading Railroad",
                "Oriental Ave", "Vermont Ave", "Connecticut Ave", "St. James Place",
                "Tennessee Ave", "New York Ave", "Kentucky Ave", "Indiana Ave",
                "Illinois Ave", "Atlantic Ave", "Ventnor Ave", "Marvin Gardens",
                "Pacific Ave", "North Carolina Ave", "Pennsylvania Ave", "Boardwalk"
        };

        int[] prices = {0, 60, 60, 200, 100, 100, 120, 140, 140, 160, 180, 180, 200, 220, 220, 280, 300, 300, 320, 400};
        this.tileCount = propertyNames.length;

        for (int i = 0; i < tileCount; i++) {
            properties.add(new PropertyTile(propertyNames[i], prices[i], PLACEHOLDER_RENT));
        }

        players = new ArrayList<>();
        inheritor inheritor = new inheritor(PLAYER_NAMES[0], PLAYER_COLORS[0]);
        clerk clerk = new clerk(PLAYER_NAMES[1], PLAYER_COLORS[1]);
        PoorMan poorman = new PoorMan(PLAYER_NAMES[2], PLAYER_COLORS[2]);
        landlord landlord = new landlord(PLAYER_NAMES[3], PLAYER_COLORS[3]);
        players.add(inheritor);
        players.add(clerk);
        players.add(poorman);
        players.add(landlord);
        initializeStocks();
    }

    // TODO: This should not be here, should be in separate use case or something - Richard
    private void initializeStocks() {
        // Temporary List of stocks just to limit API Calls:
        // TODO: Replace with actual stock data retrieval when confident this works - Richard
        List<Stock> stocks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            StockInfoDataOutputObject info = new StockInfoDataOutputObject("TEST_" + i, 100, 10, 30);
            Stock stock = new Stock(info);
            stocks.add(stock);
        }
        for (Player player: players) {
            player.initializeStocks(stocks);
        }
    }

    public void moveCurrentPlayer(int steps) {
        Player currentPlayer = getCurrentPlayer();
        if (currentPlayer.getPosition() + steps >= tileCount) {
            currentPlayer.addMoney(FINISH_LINE_BONUS);
        }
        // Note: Actual position update happens in animation
    }

    // TODO: Refactor this into a separate use cases later - Richard
    public void nextPlayer() {
        if (gameEnded) return;

        int startIndex = currentPlayerIndex;
        boolean foundNext = false;

        players.get(currentPlayerIndex).applyTurnEffects();
        totalTurns++;

        // Update stocks at the end of each round
        if (totalTurns % TURNS_PER_ROUND == 0) {
            for (Stock stock : getCurrentPlayer().getStocks().keySet()) {
                stock.updatePrice();
            }
        }

        // Check if maximum rounds reached (20 rounds = 80 turns for 4 players)
        if (totalTurns >= MAX_ROUNDS * TURNS_PER_ROUND) {
            gameEnded = true;
            gameEndReason = "Maximum 20 rounds reached";
            return;
        }

        for (int i = 1; i <= players.size(); i++) {
            int nextIndex = (startIndex + i) % players.size();
            if (!players.get(nextIndex).isBankrupt()) {
                currentPlayerIndex = nextIndex;
                foundNext = true;
                break;
            }
        }

        if (!foundNext) {
            // All players are bankrupt - game over
            gameEnded = true;
            gameEndReason = "All players are bankrupt";
            currentPlayerIndex = -1;
            return;
        }

        // Check if only one player remains solvent
        long solventPlayers = players.stream().filter(p -> !p.isBankrupt()).count();
        if (solventPlayers == 1) {
            gameEnded = true;
            gameEndReason = "Only one player remains solvent";
        }
    }

    public boolean isGameOver() {
        return gameEnded;
    }

    public String getGameEndReason() {
        return gameEndReason;
    }

    public int getCurrentRound() {
        return (totalTurns / TURNS_PER_ROUND) + 1;
    }

    public int getTotalTurns() {
        return totalTurns;
    }

    public Player getCurrentPlayer() {
        if (currentPlayerIndex == -1 || gameEnded) return null;
        return players.get(currentPlayerIndex);
    }

    public Point getTilePosition(int position, int startX, int startY, int tileSize) {
        int tilesPerSide = this.tileCount / 4;
        int cool_number = tilesPerSide * tileSize;

        if (position >= 0 && position <= tilesPerSide) {
            // Bottom row (left to right)
            return new Point(startX + position * tileSize, startY + cool_number);
        } else if (position >= (tilesPerSide+1) && position <= tilesPerSide*2) {
            // Right column (bottom to top)
            return new Point(startX + cool_number, startY + cool_number - (position - tilesPerSide) * tileSize);
        } else if (position >= (tilesPerSide*2 + 1) && position <= tilesPerSide*3) {
            // Top row (right to left)
            return new Point(startX + cool_number - (position - tilesPerSide*2) * tileSize, startY);
        } else {
            // Left column (top to bottom)
            return new Point(startX, startY + (position - tilesPerSide*3) * tileSize);
        }
    }

    /**
     * Gets the property at a specific position
     * @param position Board position
     * @return The PropertyTile at that position
     */
    public PropertyTile getPropertyAt(int position) {
        if (position >= 0 && position < properties.size()) {
            return properties.get(position);
        }
        return null;
    }

    // Getters
    public List<PropertyTile> getProperties() {
        return properties;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getTileCount() {
        return tileCount;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
}