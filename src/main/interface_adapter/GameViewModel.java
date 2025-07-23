package main.interface_adapter;

import main.use_case.GameStateSnapshot;
import main.entity.tiles.PropertyTile;
import main.entity.Player;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import main.interface_adapter.BoardPositionCalculator;
import main.interface_adapter.GameViewObserver;

/**
 * View Model holds presentation state and notifies views of changes
 */
public class GameViewModel {
    private final List<GameViewObserver> observers = new ArrayList<>();
    private final BoardPositionCalculator positionCalculator;

    // Presentation state
    private GameStateSnapshot gameState;
    private boolean rollButtonEnabled = true;
    private String errorMessage = "";
    private boolean gameOver = false;

    public GameViewModel(BoardPositionCalculator positionCalculator) {
        this.positionCalculator = positionCalculator;
    }

    public void updateGameState(GameStateSnapshot gameState) {
        this.gameState = gameState;
        notifyObservers();
    }

    public void setRollButtonEnabled(boolean enabled) {
        this.rollButtonEnabled = enabled;
        notifyObservers();
    }

    public void showError(String message) {
        this.errorMessage = message;
        notifyObservers();
    }

    public void showGameOver() {
        this.gameOver = true;
        notifyObservers();
    }

    public void startPlayerMovementAnimation(int playerIndex, int steps) {
        // Notify observers to start animation
        observers.forEach(observer -> observer.onPlayerMovementStart(playerIndex, steps));
    }

    public void notifyViewUpdate() {
        notifyObservers();
    }

    // View data getters
    public List<PropertyTile> getProperties() {
        return gameState != null ? gameState.getProperties() : new ArrayList<>();
    }

    public List<Player> getPlayers() {
        return gameState != null ? gameState.getPlayers() : new ArrayList<>();
    }

    public Player getCurrentPlayer() {
        return gameState != null ? gameState.getCurrentPlayer() : null;
    }

    public int getCurrentPlayerIndex() {
        return gameState != null ? gameState.getCurrentPlayerIndex() : -1;
    }

    public int getTileCount() {
        return gameState != null ? gameState.getTileCount() : 0;
    }

    public boolean isRollButtonEnabled() { return rollButtonEnabled; }
    public String getErrorMessage() { return errorMessage; }
    public boolean isGameOver() { return gameOver; }

    public Point getTilePosition(int position, int startX, int startY, int tileSize) {
        return positionCalculator.getTilePosition(position, startX, startY, tileSize, getTileCount());
    }

    // Observer pattern
    public void addObserver(GameViewObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameViewObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        observers.forEach(GameViewObserver::onGameStateChanged);
    }
}