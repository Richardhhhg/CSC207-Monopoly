package main.use_case;

public class NextPlayerResult {
    private final boolean success;
    private final int nextPlayerIndex;
    private final boolean gameOver;

    public NextPlayerResult(boolean success, int nextPlayerIndex, boolean gameOver) {
        this.success = success;
        this.nextPlayerIndex = nextPlayerIndex;
        this.gameOver = gameOver;
    }

    // Getters
    public boolean isSuccess() { return success; }
    public int getNextPlayerIndex() { return nextPlayerIndex; }
    public boolean isGameOver() { return gameOver; }
}