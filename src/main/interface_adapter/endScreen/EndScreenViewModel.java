package main.interface_adapter.endScreen;

import java.util.List;

import main.entity.players.AbstractPlayer;

public class EndScreenViewModel {
    private final String gameOverTitle;
    private final String gameEndReason;
    private final String totalRoundsText;
    private final String winnerText;
    private final List<PlayerDisplayData> playerDisplayData;
    private final String newGameButtonText;
    private final String exitButtonText;

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

    public static class PlayerDisplayData {
        private final AbstractPlayer player;
        private final int rank;
        private final String moneyText;
        private final String propertyValueText;
        private final String stockValueText;
        private final String netWorthText;
        private final String statusText;

        public PlayerDisplayData(AbstractPlayer player, int rank, String moneyText,
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

        public AbstractPlayer getPlayer() {
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
