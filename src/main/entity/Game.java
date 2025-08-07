package main.entity;

import java.awt.Point;
import java.io.IOException;
import java.util.List;

import main.constants.Constants;
import main.entity.players.Player;
import main.entity.stocks.Stock;
import main.entity.tiles.Tile;
import main.use_case.game.GameInitializePlayers;
import main.use_case.game.GameInitializeStocks;
import main.use_case.game.GameInitializeTiles;

/**
 * GameBoard manages the game state and logic, separate from UI concerns.
 */
public class Game {
    private List<Tile> tiles;
    private List<Player> players;
    private List<Stock> stocks;
    private int currentPlayerIndex;
    private int tileCount;
    private int totalTurns;
    private int currentRound = 1;
    private int turnsInCurrentRound;
    private int roundStartPlayerIndex;
    private boolean gameEnded;
    private String gameEndReason = "";

    public Game() {
        initializeGame();
    }

    /**
     * Initializes Properties and Players.
     */
    public void initializeGame() {
        try {
            new GameInitializeTiles(this).execute();
            new GameInitializePlayers(this).execute();
            new GameInitializeStocks(this).execute();
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public boolean isGameOver() {
        return gameEnded;
    }

    public String getGameEndReason() {
        return gameEndReason;
    }

    /**
     * Get the current round number (1-based).
     * @return current round number
     */
    public int getCurrentRound() {
        return currentRound;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Helper function for getting the position of a tile on the board.
     * @param position The position of the tile (0-indexed)
     * @param startX The starting X coordinate of the board
     * @param startY The starting Y coordinate of the board
     * @param tileSize The size of each tile
     * @return The Point representing the (x, y) coordinates of the tile
     */
    public Point getTilePosition(int position, int startX, int startY, int tileSize) {
        final int tilesPerSide = this.tileCount / 4;
        final int tileSidePixels = tilesPerSide * tileSize;
        final Point point;

        if (position >= 0 && position <= tilesPerSide) {
            // Bottom row (left to right)
            point = new Point(startX + position * tileSize, startY + tileSidePixels);
        }
        else if (position >= (tilesPerSide + 1) && position <= tilesPerSide * 2) {
            // Right column (bottom to top)
            point = new Point(startX + tileSidePixels, startY + tileSidePixels - (position - tilesPerSide)
                    * tileSize);
        }
        else if (position >= (tilesPerSide * 2) && position <= tilesPerSide * Constants.TILES_PER_SIDE_ADJ_3) {
            // Top row (right to left)
            point = new Point(startX + tileSidePixels - (position - tilesPerSide * 2) * tileSize, startY);
        }
        else {
            // Left column (top to bottom)
            point = new Point(startX, startY + (position - tilesPerSide * Constants.TILES_PER_SIDE_ADJ_3)
                    * tileSize);
        }
        return point;
    }

    /**
     * Gets the property at a specific position.
     * @param position Board position
     * @return The PropertyTile at that position
     */
    public Tile getPropertyAt(int position) {
        return tiles.get(position);
    }

    public List<Tile> getTiles() {
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

    public int getRoundStartPlayerIndex() {
        return roundStartPlayerIndex;
    }

    public boolean getGameEnded() {
        return gameEnded;
    }

    /**
     * Increment total turns and track turns in current round.
     */
    public void increaseTurn() {
        totalTurns++;
        turnsInCurrentRound++;
    }

    /**
     * Start a new round - reset turn counter, increment round number, and set new round start player.
     * @param newRoundStartPlayerIndex The index of the player who will start the new round
     */
    public void startNewRound(int newRoundStartPlayerIndex) {
        // Check if we've reached the maximum number of rounds BEFORE incrementing
        if (currentRound >= Constants.MAX_ROUNDS) {
            endGame("Maximum " + Constants.MAX_ROUNDS + " rounds reached");
        }

        currentRound++;
        turnsInCurrentRound = 0;
        roundStartPlayerIndex = newRoundStartPlayerIndex;
    }

    /**
     * Check if the current round is complete.
     * A round is complete when we've cycled back to the player who started the round
     * and all active players have had at least one turn
     * @param nextPlayerIndex The index of the player who would take the next turn
     * @return true if the round is complete, false otherwise
     */
    public boolean isRoundComplete(int nextPlayerIndex) {
        // Round is complete when:
        // 1. We have at least one turn in this round
        // 2. The next player would be the one who started the round (or the next active player after them if they died)
        // Find the next active player from the round start position
        final int expectedRoundStartPlayer = findNextActivePlayerFrom(roundStartPlayerIndex - 1);
        return nextPlayerIndex == expectedRoundStartPlayer;
    }

    /**
     * Find the next active player starting from a given index.
     * @param startIndex The index to start searching from
     * @return The index of the next active player, or -1 if no active players are found
     */
    public int findNextActivePlayerFrom(int startIndex) {
        int result = -1;
        for (int i = 1; i <= players.size(); i++) {
            final int candidateIndex = (startIndex + i) % players.size();
            if (!players.get(candidateIndex).isBankrupt()) {
                result = candidateIndex;
            }
        }
        return result;
    }

    /**
     * Set the current player index.
     * @param index The index of the player to set as current
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public void setCurrentPlayerIndex(int index) throws IndexOutOfBoundsException {
        if (index >= 0 && index < players.size()) {
            this.currentPlayerIndex = index;
        }
        else {
            throw new IndexOutOfBoundsException("Invalid player index: " + index);
        }
    }

    /**
     * Ends the game with a specified message.
     * @param message The reason for ending the game, can be null or empty
     */
    public void endGame(String message) {
        this.gameEnded = true;
        if (message == null || message.isEmpty()) {
            this.gameEndReason = "Game Over";
        }
        else {
            this.gameEndReason = message;
        }
    }

    /**
     * Sets the tiles for the game.
     * @param tiles List of tiles to set for the game
     * @throws IllegalArgumentException if the tiles list is null or empty
     */
    public void setTiles(List<Tile> tiles) throws IllegalArgumentException {
        if (tiles != null && !tiles.isEmpty()) {
            this.tiles = tiles;
            this.tileCount = tiles.size();
        }
        else {
            throw new IllegalArgumentException("Tiles list cannot be null or empty");
        }
    }

    /**
     * Sets the players for the game.
     * @param players List of players to set for the game
     * @throws IllegalArgumentException if the players list is null or empty
     */
    public void setPlayers(List<Player> players) throws IllegalArgumentException {
        if (players != null && !players.isEmpty()) {
            this.players = players;
        }
        else {
            throw new IllegalArgumentException("Players list cannot be null or empty");
        }
    }

    /**
     * Sets the stocks for the game.
     * @param stocks List of stocks to set for the game
     * @throws IllegalArgumentException if the stocks list is null or empty
     */
    public void setStocks(List<Stock> stocks) throws IllegalArgumentException {
        if (stocks != null && !stocks.isEmpty()) {
            this.stocks = stocks;
        }
        else {
            throw new IllegalArgumentException("Stocks list cannot be null or empty");
        }
    }
}
