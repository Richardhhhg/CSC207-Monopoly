package main.use_case.player;

import main.entity.players.AbstractPlayer;
import main.entity.tiles.PropertyTile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeclareBankruptcyTest {

    static class TestAbstractPlayer extends AbstractPlayer {
        private final List<PropertyTile> properties = new ArrayList<>();

        @Override
        public List<PropertyTile> getProperties() {
            return properties;
        }

        @Override
        public boolean isBankrupt() {
            return true;
        }

        public void addProperty(PropertyTile tile) {
            properties.add(tile);
        }
    }

    static class TestPropertyTile extends PropertyTile {
        public boolean setOwnedCalled = false;
        public AbstractPlayer lastOwner = null;

        public TestPropertyTile(String name, int price, float rent) {
            super(name, price, rent);
        }

        @Override
        public void setOwned(boolean owned, AbstractPlayer owner) {
            setOwnedCalled = true;
            lastOwner = owner;
            super.setOwned(owned, owner);
        }
    }

    @Test
    void testBankruptcyClearsPropertiesAndOwnership() {
        TestAbstractPlayer player = new TestAbstractPlayer();
        TestPropertyTile tile1 = new TestPropertyTile("Tile1", 100, 10.0f);
        TestPropertyTile tile2 = new TestPropertyTile("Tile2", 150, 12.5f);
        player.addProperty(tile1);
        player.addProperty(tile2);

        DeclareBankruptcy bankruptcy = new DeclareBankruptcy();
        bankruptcy.execute(player);

        assertTrue(tile1.setOwnedCalled, "setOwned should be called on tile1");
        assertNull(tile1.lastOwner, "Owner should be null after bankruptcy");
        assertTrue(tile2.setOwnedCalled, "setOwned should be called on tile2");
        assertNull(tile2.lastOwner, "Owner should be null after bankruptcy");
        assertEquals(0, player.getProperties().size(), "Player properties should be cleared after bankruptcy");
    }

    @Test
    void testNullPlayerDoesNothing() {
        DeclareBankruptcy bankruptcy = new DeclareBankruptcy();
        bankruptcy.execute(null);
    }
}