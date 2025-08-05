package main.entity;

import main.entity.Stocks.Stock;
import main.entity.tiles.PropertyTile;
import main.use_case.Game.GameInitializePlayers;
import main.use_case.Game.GameInitializeStocks;
import main.use_case.Game.GameInitializeTiles;
import main.entity.players.Player;
import main.use_case.Tile;

import java.util.List;
import java.awt.*;

import static main.Constants.Constants.FINISH_LINE_BONUS;
import static main.Constants.Constants.MAX_ROUNDS;

/**
 * GameBoard manages the game state and logic, separate from UI concerns.
 */
public class Game {
    private List<PropertyTile> tiles;
    private List<Player> players;
    private List<Stock> stocks;
    private int currentPlayerIndex = 0;
    private int tileCount;
    private int totalTurns = 0;
    private int currentRound = 1;
    private int turnsInCurrentRound = 0;
    private boolean gameEnded = false;
    private String gameEndReason = "";

    public Game() {
        initializeGame();
    }

    /**
     * Initializes Properties and Players
     */
    public void initializeGame() {
        new GameInitializeTiles(this).execute();
        new GameInitializePlayers(this).execute();
        new GameInitializeStocks(this).execute();
    }

    public boolean isGameOver() {
        return gameEnded;
    }

    public String getGameEndReason() {
        return gameEndReason;
    }

    /**
     * Get the current round number (1-based)
     */
    public int getCurrentRound() {
        return currentRound;
    }

    /**
     * Get the number of active (non-bankrupt) players
     */
    public int getActivePlayers() {
        return (int) players.stream().filter(p -> !p.isBankrupt()).count();
    }

    /**
     * Get turns completed in the current round
     */
    public int getTurnsInCurrentRound() {
        return turnsInCurrentRound;
    }

    public int getTotalTurns() {
        return totalTurns;
    }

    public Player getCurrentPlayer() {
        if (currentPlayerIndex == -1 || gameEnded) return null;
        return players.get(currentPlayerIndex);
    }

    /**
     * Helper function for getting the position of a tile on the board
     * @param position
     * @param startX
     * @param startY
     * @param tileSize
     * @return
     */
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
    public Tile getPropertyAt(int position) {
        if (position >= 0 && position < tiles.size()) {
            return tiles.get(position);
        }
        return null;
    }

    public List<PropertyTile> getTiles() {
        return tiles;
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

    public List<Stock> getStocks() {
        return stocks;
    }

    public boolean getGameEnded() {
        return gameEnded;
    }

    /**
     * Increment total turns and track turns in current round
     */
    public void increaseTurn() {
        totalTurns++;
        turnsInCurrentRound++;
    }

    /**
     * Start a new round - reset turn counter and increment round number
     */
    public void startNewRound() {
        currentRound++;
        turnsInCurrentRound = 0;

        // Check if we've reached the maximum number of rounds
        if (currentRound > MAX_ROUNDS) {
            endGame("Maximum " + MAX_ROUNDS + " rounds reached");
        }
    }

    /**
     * Check if the current round is complete
     * A round is complete when each active player has had one turn
     */
    public boolean isRoundComplete() {
        int activePlayers = getActivePlayers();
        return turnsInCurrentRound >= activePlayers;
    }

    public void setCurrentPlayerIndex(int index) {
        if (index >= 0 && index < players.size()) {
            this.currentPlayerIndex = index;
        } else {
            throw new IndexOutOfBoundsException("Invalid player index: " + index);
        }
    }

    public void endGame(String message) {
        this.gameEnded = true;
        this.gameEndReason = message != null && !message.isEmpty() ? message : "Game Over";
    }

    public void setTiles(List<PropertyTile> tiles) {
        if (tiles != null && !tiles.isEmpty()) {
            this.tiles = tiles;
            this.tileCount = tiles.size();
        } else {
            throw new IllegalArgumentException("Tiles list cannot be null or empty");
        }
    }

    public void setPlayers(List<Player> players) {
        if (players != null && !players.isEmpty()) {
            this.players = players;
        } else {
            throw new IllegalArgumentException("Players list cannot be null or empty");
        }
    }

    public void setTileCount(int tileCount) {
        if (tileCount > 0) {
            this.tileCount = tileCount;
        } else {
            throw new IllegalArgumentException("Tile count must be greater than zero");
        }
    }

    public void setStocks(List<Stock> stocks) {
        if (stocks != null && !stocks.isEmpty()) {
            this.stocks = stocks;
        } else {
            throw new IllegalArgumentException("Stocks list cannot be null or empty");
        }
    }
}