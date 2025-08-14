package main.use_case.game;

import main.entity.Game;
import main.entity.players.AbstractPlayer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameMoveCurrentAbstractPlayerTest {

    static class TestAbstractPlayer extends AbstractPlayer {
        private int position;
        private float money;

        public TestAbstractPlayer(int position, float money) {
            this.position = position;
            this.money = money;
        }

        @Override
        public int getPosition() {
            return position;
        }

        @Override
        public void addMoney(float amount) {
            money += amount;
        }

        @Override
        public float getMoney() {
            return money;
        }
    }

    static class TestGame extends Game {
        private boolean gameEnded = false;
        private TestAbstractPlayer player;
        private int tileCount;

        public TestGame(TestAbstractPlayer player, int tileCount) {
            this.player = player;
            this.tileCount = tileCount;
        }

        @Override
        public boolean getGameEnded() {
            return gameEnded;
        }

        @Override
        public AbstractPlayer getCurrentPlayer() {
            return player;
        }

        @Override
        public int getTileCount() {
            return tileCount;
        }
    }

    @Test
    void testFinishLineBonusAwarded() {
        TestAbstractPlayer player = new TestAbstractPlayer(9, 100.0f);
        TestGame game = new TestGame(player, 10);

        GameMoveCurrentPlayer useCase = new GameMoveCurrentPlayer(game);
        useCase.execute(2);

        assertTrue(player.getMoney() > 100.0f, "Player should receive FINISH_LINE_BONUS");
    }

    @Test
    void testNoBonusIfNotCrossedFinishLine() {
        TestAbstractPlayer player = new TestAbstractPlayer(5, 100.0f);
        TestGame game = new TestGame(player, 10);

        GameMoveCurrentPlayer useCase = new GameMoveCurrentPlayer(game);
        useCase.execute(2);

        assertEquals(100.0f, player.getMoney(), "Player should not receive FINISH_LINE_BONUS");
    }
}
