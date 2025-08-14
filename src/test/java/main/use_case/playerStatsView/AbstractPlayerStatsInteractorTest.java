package main.use_case.playerStatsView;

import main.entity.Game;
import main.entity.players.AbstractPlayer;
import main.entity.tiles.PropertyTile;
import main.use_case.playerStats.PlayerStatsInteractor;
import main.use_case.playerStats.PlayerStatsOutput;
import main.use_case.playerStats.PlayerStatsOutputBoundary;
import main.use_case.playerStats.PlayerStatsOutputData;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.Arrays;

import static org.junit.Assert.*;

public class AbstractPlayerStatsInteractorTest {

    private Game game;
    private NamedPropertyTile p1Tile;
    private NamedPropertyTile p2Tile;
    private CapturingPresenter presenter;
    private PlayerStatsInteractor interactor;

    @Before
    public void setUp() {
        game = new Game();
        p1Tile = new NamedPropertyTile("Boardwalk");
        p2Tile = new NamedPropertyTile("Park Place");
        presenter = new CapturingPresenter();
        interactor = new PlayerStatsInteractor(presenter);
    }

    @Test
    public void execute_twoPlayers_collectsAllFieldsAndProperties() {
        TestAbstractPlayer alice = new TestAbstractPlayer("Alice", 1234.5f, false, Color.RED);
        TestAbstractPlayer bob   = new TestAbstractPlayer("Bob", 50.0f,  true,  Color.BLUE);

        alice.getProperties().add(p1Tile);
        alice.getProperties().add(p2Tile);

        game.setPlayers(Arrays.<AbstractPlayer>asList(alice, bob));

        interactor.execute(game);

        PlayerStatsOutputData out = presenter.lastOutput;
        assertNotNull(out);
        assertEquals(2, out.getPlayerStats().size());

        PlayerStatsOutput s0 = out.getPlayerStats().get(0);
        assertEquals("Alice", s0.getName());
        assertEquals(1234.5f, s0.getMoney(), 0.0001f);
        assertFalse(s0.isBankrupt());
        assertEquals(Color.RED, s0.getColor());
        assertEquals(Arrays.asList("Boardwalk", "Park Place"), s0.getPropertyNames());

        PlayerStatsOutput s1 = out.getPlayerStats().get(1);
        assertEquals("Bob", s1.getName());
        assertEquals(50.0f, s1.getMoney(), 0.0001f);
        assertTrue(s1.isBankrupt());
        assertEquals(Color.BLUE, s1.getColor());
        assertTrue(s1.getPropertyNames().isEmpty());
    }

    @Test
    public void execute_NullProperties() {
        TestAbstractPlayer carol = new TestAbstractPlayer("Carol", 200f, false, Color.GREEN, true); // properties = null
        game.setPlayers(Arrays.<AbstractPlayer>asList(carol));

        interactor.execute(game);

        PlayerStatsOutputData out = presenter.lastOutput;
        assertNotNull(out);
        assertEquals(1, out.getPlayerStats().size());

        PlayerStatsOutput s = out.getPlayerStats().get(0);
        assertEquals("Carol", s.getName());
        assertEquals(200f, s.getMoney(), 0.0001f);
        assertFalse(s.isBankrupt());
        assertEquals(Color.GREEN, s.getColor());
        assertTrue("Null properties should result in empty names list", s.getPropertyNames().isEmpty());
    }


    /** Presenter that captures the last output for assertions. */
    private static class CapturingPresenter implements PlayerStatsOutputBoundary {
        PlayerStatsOutputData lastOutput;
        @Override public void presentPlayerStats(PlayerStatsOutputData outputData) {
            this.lastOutput = outputData;
        }
    }

    /** Minimal concrete Player for testing. */
    private static class TestAbstractPlayer extends AbstractPlayer {
        TestAbstractPlayer(String name, float money, boolean bankrupt, Color color) {
            super(name, money, color);
            this.bankrupt = bankrupt;
        }
        TestAbstractPlayer(String name, float money, boolean bankrupt, Color color, boolean makePropertiesNull) {
            this(name, money, bankrupt, color);
            if (makePropertiesNull) {
                this.properties = null;
            }
        }
    }

    /**
     * Tiny Fake PropertyTile subclass that only supplies a name.
     */
    private static class NamedPropertyTile extends PropertyTile {
        private final String name;
        NamedPropertyTile(String name) {
            super(null, 0, 50);
            this.name = name;
        }
        @Override public String getName() { return name; }
    }
}

