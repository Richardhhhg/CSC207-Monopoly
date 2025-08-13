package main.use_case.player;

import main.entity.players.Player;
import main.entity.players.applyAfterEffects;
import main.entity.tiles.PropertyTile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApplyTurnEffectsTest {

    static class TestPlayer extends Player implements applyAfterEffects {
        boolean effectsApplied = false;
        boolean bankrupt = false;

        @Override
        public void applyTurnEffects() {
            effectsApplied = true;
        }

        @Override
        public boolean isBankrupt() {
            return bankrupt;
        }

        @Override
        public List<PropertyTile> getProperties() {
            return new ArrayList<>();
        }
    }

    static class SimplePlayer extends Player {
        boolean bankrupt = false;

        @Override
        public boolean isBankrupt() {
            return bankrupt;
        }

        @Override
        public List<PropertyTile> getProperties() {
            return new ArrayList<>();
        }
    }

    @Test
    void testApplyTurnEffectsIsCalled() {
        TestPlayer player = new TestPlayer();
        player.bankrupt = false;

        ApplyTurnEffects effects = new ApplyTurnEffects();
        effects.execute(player);

        assertTrue(player.effectsApplied, "applyTurnEffects should be called if player is applyAfterEffects");
    }

    @Test
    void testDeclareBankruptcyIsCalled() {
        SimplePlayer player = new SimplePlayer();
        player.bankrupt = true;
        ApplyTurnEffects effects = new ApplyTurnEffects();
        effects.execute(player);

        assertEquals(0, player.getProperties().size(), "Properties should be cleared if player is bankrupt");
    }
}