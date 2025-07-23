package main.view;

import main.entity.*;
import main.entity.tiles.Property;
import main.use_case.Player;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;

import static main.Constants.Constants.FINISH_LINE_BONUS;

/**
 * GameBoard manages the game state and logic, including end game conditions.
 */
public class GameBoard {
    private static final int PLACEHOLDER_RENT = 50;
    private static final int PLAYER_COUNT = 4;
    private static final int MAX_TURNS_PER_PLAYER = 20;
    private static final Color[] PLAYER_COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
    private static final String[] PLAYER_NAMES = {"Player 1", "Player 2", "Player 3", "Player 4"};

    private ArrayList<Property> properties;
    private List<Player> players;
    private int currentPlayerIndex = 0;
    private int tileCount;

    // End game related fields
    private int totalTurns = 0;
    private int maxTotalTurns;
    private boolean gameEnded = false;
    private Player winner = null;
    private String winCondition = "";

    public GameBoard() {
        this.maxTotalTurns = MAX_TURNS_PER_PLAYER * PLAYER_COUNT; // 80 turns total
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
            properties.add(new Property(propertyNames[i], prices[i], PLACEHOLDER_RENT));
        }

        // Initialize players
        players = new ArrayList<>();
        DefaultPlayer defaultPlayer = new DefaultPlayer(PLAYER_NAMES[0], PLAYER_COLORS[0]);
        clerk clerk = new clerk(PLAYER_NAMES[1], PLAYER_COLORS[1]);
        collegeStudent collegeStudent = new collegeStudent(PLAYER_NAMES[2], PLAYER_COLORS[2]);
        landlord landlord = new landlord(PLAYER_NAMES[3], PLAYER_COLORS[3]);
        players.add(defaultPlayer);
        players.add(clerk);
        players.add(collegeStudent);
        players.add(landlord);
    }

    public void moveCurrentPlayer(int steps) {
        Player currentPlayer = getCurrentPlayer();
        if (currentPlayer != null && currentPlayer.getPosition() + steps >= tileCount) {
            currentPlayer.addMoney(FINISH_LINE_BONUS);
        }
        // Note: Actual position update happens in animation
    }

    /**
     * Advances to the next player and checks for end game conditions.
     * @return true if the game has ended, false if it continues
     */
    public boolean nextPlayer() {
        int startIndex = currentPlayerIndex;

        // Apply turn effects for current player before switching
        if (currentPlayerIndex >= 0 && currentPlayerIndex < players.size()) {
            players.get(currentPlayerIndex).applyTurnEffects();
        }

        // Increment turn counter
        totalTurns++;

        // Check for end conditions after the turn
        if (checkEndConditions()) {
            showEndScreen();
            return true; // Game ended
        }

        // Find next non-bankrupt player
        boolean foundNext = false;
        for (int i = 1; i <= players.size(); i++) {
            int nextIndex = (startIndex + i) % players.size();
            if (!players.get(nextIndex).isBankrupt()) {
                currentPlayerIndex = nextIndex;
                foundNext = true;
                break;
            }
        }

        if (!foundNext) {
            // All players are bankrupt - this should be caught by end conditions
            currentPlayerIndex = -1;
            if (!gameEnded) {
                checkEndConditions();
                showEndScreen();
            }
            return true; // Game ended
        }

        return false; // Game continues
    }

    /**
     * Checks if the game should end based on current conditions.
     * @return true if game should end, false otherwise
     */
    private boolean checkEndConditions() {
        if (gameEnded) {
            return true;
        }

        // Count non-bankrupt players
        List<Player> activePlayers = players.stream()
                .filter(player -> !player.isBankrupt())
                .toList();

        // End condition 1: Only one player left
        if (activePlayers.size() <= 1) {
            gameEnded = true;
            if (activePlayers.size() == 1) {
                winner = activePlayers.get(0);
                winCondition = "Last Player Standing";
            } else {
                // All players bankrupt - shouldn't happen but handle gracefully
                winner = findWealthiestPlayer(players);
                winCondition = "All Players Bankrupt - Highest Cash Wins";
            }
            return true;
        }

        // End condition 2: Maximum turns reached
        if (totalTurns >= maxTotalTurns) {
            gameEnded = true;
            winner = findWealthiestPlayer(activePlayers);
            winCondition = "20 Turns Completed - Highest Cash Wins";
            return true;
        }

        return false;
    }

    /**
     * Finds the player with the highest cash amount.
     */
    private Player findWealthiestPlayer(List<Player> playerList) {
        return playerList.stream()
                .max((p1, p2) -> Float.compare(p1.getMoney(), p2.getMoney()))
                .orElse(null);
    }

    /**
     * Shows the end screen with game results.
     */
    private void showEndScreen() {
        if (!gameEnded) {
            return;
        }
        new EndScreen(players, winner, winCondition, totalTurns, maxTotalTurns);
    }

    public boolean isGameOver() {
        return gameEnded || currentPlayerIndex == -1;
    }

    public Player getCurrentPlayer() {
        if (currentPlayerIndex == -1 || currentPlayerIndex >= players.size()) return null;
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

    // Getters for basic game state
    public List<Property> getProperties() {
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

    // Getters for end game information
    public int getTotalTurns() {
        return totalTurns;
    }

    public int getMaxTotalTurns() {
        return maxTotalTurns;
    }

    public int getRemainingTurns() {
        return Math.max(0, maxTotalTurns - totalTurns);
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public Player getWinner() {
        return winner;
    }

    public String getWinCondition() {
        return winCondition;
    }

    public int getCurrentRound() {
        return (totalTurns / players.size()) + (totalTurns % players.size() > 0 ? 1 : 0);
    }

    public String getGameStatus() {
        if (isGameOver()) {
            return "Game Over";
        }
        return String.format("Round %d/20 - Turn %d/%d",
                getCurrentRound(), totalTurns, maxTotalTurns);
    }
}