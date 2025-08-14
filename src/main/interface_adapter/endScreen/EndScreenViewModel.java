package main.interface_adapter.endScreen;

import java.util.List;
import main.entity.players.Player;

/**
 * Mutable ViewModel for the EndScreen.
 * Allows the presenter to update the view state with processed data.
 */
public class EndScreenViewModel {
    private String gameOverTitle = "";
    private String gameEndReason = "";
    private String totalRoundsText = "";
    private String winnerText = "";
    private List<PlayerDisplayData> playerDisplayData = null;
    private String newGameButtonText = "New Game";
    private String exitButtonText = "Exit";

    /**
     * Default constructor for mutable view model.
     */
    public EndScreenViewModel() {
    }

    /**
     * Constructor with initial values (for backward compatibility).
     */
    public EndScreenViewModel(String gameOverTitle, String gameEndReason, String totalRoundsText,
                              String winnerText, List<PlayerDisplayData> playerDisplayData,
                              String newGameButtonText, String exitButtonText) {
        this.gameOverTitle = gameOverTitle;
        this.gameEndReason = gameEndReason;
        this.totalRoundsText = totalRoundsText;
        this.winnerText = winnerText;
        this.playerDisplayData = playerDisplayData;
        this.newGameButtonText = newGameButtonText;
        this.exitButtonText = exitButtonText;
    }

    // Getters
    public String getGameOverTitle() {
        return gameOverTitle;
    }

    public String getGameEndReason() {
        return gameEndReason;
    }

    public String getTotalRoundsText() {
        return totalRoundsText;
    }

    public String getWinnerText() {
        return winnerText;
    }

    public List<PlayerDisplayData> getPlayerDisplayData() {
        return playerDisplayData;
    }

    public String getNewGameButtonText() {
        return newGameButtonText;
    }

    public String getExitButtonText() {
        return exitButtonText;
    }

    // Setters for presenter to update view model
    public void setGameOverTitle(String gameOverTitle) {
        this.gameOverTitle = gameOverTitle;
    }

    public void setGameEndReason(String gameEndReason) {
        this.gameEndReason = gameEndReason;
    }

    public void setTotalRoundsText(String totalRoundsText) {
        this.totalRoundsText = totalRoundsText;
    }

    public void setWinnerText(String winnerText) {
        this.winnerText = winnerText;
    }

    public void setPlayerDisplayData(List<PlayerDisplayData> playerDisplayData) {
        this.playerDisplayData = playerDisplayData;
    }

    public void setNewGameButtonText(String newGameButtonText) {
        this.newGameButtonText = newGameButtonText;
    }

    public void setExitButtonText(String exitButtonText) {
        this.exitButtonText = exitButtonText;
    }

    /**
     * Updates all view model data at once.
     */
    public void updateAllData(String gameOverTitle, String gameEndReason, String totalRoundsText,
                              String winnerText, List<PlayerDisplayData> playerDisplayData,
                              String newGameButtonText, String exitButtonText) {
        this.gameOverTitle = gameOverTitle;
        this.gameEndReason = gameEndReason;
        this.totalRoundsText = totalRoundsText;
        this.winnerText = winnerText;
        this.playerDisplayData = playerDisplayData;
        this.newGameButtonText = newGameButtonText;
        this.exitButtonText = exitButtonText;
    }

    /**
     * Data class representing display information for a single player.
     */
    public static class PlayerDisplayData {
        private final Player player;
        private final int rank;
        private final String moneyText;
        private final String propertyValueText;
        private final String stockValueText;
        private final String netWorthText;
        private final String statusText;

        public PlayerDisplayData(Player player, int rank, String moneyText,
                                 String propertyValueText, String stockValueText, String netWorthText,
                                 String statusText) {
            this.player = player;
            this.rank = rank;
            this.moneyText = moneyText;
            this.propertyValueText = propertyValueText;
            this.stockValueText = stockValueText;
            this.netWorthText = netWorthText;
            this.statusText = statusText;
        }

        public Player getPlayer() {
            return player;
        }

        public String getMoneyText() {
            return moneyText;
        }

        public String getPropertyValueText() {
            return propertyValueText;
        }

        public String getStockValueText() {
            return stockValueText;
        }

        public String getNetWorthText() {
            return netWorthText;
        }

        public String getStatusText() {
            return statusText;
        }

        public String getRankText() {
            return "#" + rank + " - " + player.getName();
        }
    }
}