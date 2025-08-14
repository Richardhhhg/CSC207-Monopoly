package main.use_case.player;

import main.entity.players.AbstractPlayer;
import main.entity.players.applyAfterEffects;
import main.entity.tiles.PropertyTile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApplyTurnEffectsTest {

    static class TestAbstractPlayer extends AbstractPlayer implements applyAfterEffects {
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

    static class SimpleAbstractPlayer extends AbstractPlayer {
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
        TestAbstractPlayer player = new TestAbstractPlayer();
        player.bankrupt = false;

        ApplyTurnEffects effects = new ApplyTurnEffects();
        effects.execute(player);

        assertTrue(player.effectsApplied, "applyTurnEffects should be called if player is applyAfterEffects");
    }

    @Test
    void testDeclareBankruptcyIsCalled() {
        SimpleAbstractPlayer player = new SimpleAbstractPlayer();
        player.bankrupt = true;
        ApplyTurnEffects effects = new ApplyTurnEffects();
        effects.execute(player);

        assertEquals(0, player.getProperties().size(), "Properties should be cleared if player is bankrupt");
    }
}