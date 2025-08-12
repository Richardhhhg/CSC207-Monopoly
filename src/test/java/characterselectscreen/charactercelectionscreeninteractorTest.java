package characterselectscreen;

import main.use_case.characterSelectionScreen.CharacterSelectionInputData;
import main.use_case.characterSelectionScreen.CharacterSelectionPlayerViewModel;
import main.use_case.characterSelectionScreen.CharacterSelectionScreenDataAccessInterface;
import main.use_case.characterSelectionScreen.CharacterSelectionScreenInteractor;
import main.use_case.characterSelectionScreen.CharacterSelectionScreenOutputBoundary;

import org.junit.Before;
import org.junit.Test;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class charactercelectionscreeninteractorTest {

    private CharacterSelectionScreenInteractor interactor;
    private StubPresenter presenter;

    private static class StubPresenter implements CharacterSelectionScreenOutputBoundary {
        List<CharacterSelectionPlayerViewModel> lastLaunchData;
        CharacterSelectionPlayerViewModel lastPlayerData;
        Integer lastPlayerIndex;

        @Override
        public void prepareLaunchData(List<CharacterSelectionPlayerViewModel> players) {
            this.lastLaunchData = players;
        }

        @Override
        public void preparePlayer(CharacterSelectionPlayerViewModel data, int index) {
            this.lastPlayerData = data;
            this.lastPlayerIndex = index;
        }
    }

    private static class StubDao implements CharacterSelectionScreenDataAccessInterface {}

    @Before
    public void setUp() {
        presenter = new StubPresenter();
        interactor = new CharacterSelectionScreenInteractor(presenter, new StubDao());
    }

    @Test
    public void testExecuteSetsPlayerAndCallsPresenter() {
        CharacterSelectionInputData input = new CharacterSelectionInputData(0, "Alice", "Poor Man");
        interactor.execute(input);
        assertNotNull(presenter.lastPlayerData);
        assertEquals("Alice", presenter.lastPlayerData.getName());
        assertEquals("Poor Man", presenter.lastPlayerData.getType());
        assertEquals(Color.RED, presenter.lastPlayerData.getColor());
        assertNotNull(presenter.lastPlayerData.getPortrait());
        assertEquals(Integer.valueOf(0), presenter.lastPlayerIndex);
    }

    @Test
    public void testExecuteOverwritesExistingPlayer() {
        interactor.execute(new CharacterSelectionInputData(0, "Alice", "Poor Man"));
        interactor.execute(new CharacterSelectionInputData(0, "Bob", "Clerk"));
        assertNotNull(presenter.lastPlayerData);
        assertEquals("Bob", presenter.lastPlayerData.getName());
        assertEquals("Clerk", presenter.lastPlayerData.getType());
        assertEquals(Integer.valueOf(0), presenter.lastPlayerIndex);
    }

    @Test
    public void testColorsAreAssignedByIndex() {
        Color[] expectedColors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE};
        for (int i = 0; i < 4; i++) {
            interactor.execute(new CharacterSelectionInputData(i, "P" + i, "T" + i));
            assertEquals(expectedColors[i], presenter.lastPlayerData.getColor());
            assertEquals(Integer.valueOf(i), presenter.lastPlayerIndex);
        }
    }

    @Test
    public void testConfirmSelectionWithNoPlayers() {
        interactor.confirmSelection();
        assertNotNull(presenter.lastLaunchData);
        assertTrue(presenter.lastLaunchData.isEmpty());
    }

    @Test
    public void testConfirmSelectionWithSomePlayers() {
        interactor.execute(new CharacterSelectionInputData(0, "Alice", "Clerk"));
        interactor.execute(new CharacterSelectionInputData(2, "Bob", "Landlord"));
        interactor.confirmSelection();
        assertNotNull(presenter.lastLaunchData);
        assertEquals(2, presenter.lastLaunchData.size());
        List<String> names = new ArrayList<String>();
        for (CharacterSelectionPlayerViewModel vm : presenter.lastLaunchData) {
            names.add(vm.getName());
        }
        assertTrue(names.contains("Alice"));
        assertTrue(names.contains("Bob"));
    }

    @Test
    public void testCanStartGameFalseWhenNoPlayers() {
        assertFalse(interactor.canStartGame());
    }

    @Test
    public void testCanStartGameWhenNotEnoughPlayers() {
        interactor.execute(new CharacterSelectionInputData(0, "Alice", "Clerk"));
        assertFalse(interactor.canStartGame());
    }

    @Test
    public void testCanStartGameTrueWhenEnoughPlayers() {
        interactor.execute(new CharacterSelectionInputData(0, "Alice", "Clerk"));
        interactor.execute(new CharacterSelectionInputData(1, "Bob", "Landlord"));
        assertTrue(interactor.canStartGame());
    }

    @Test
    public void testCanStartGameSameTypePlayers() {
        interactor.execute(new CharacterSelectionInputData(0, "Alice", "Clerk"));
        interactor.execute(new CharacterSelectionInputData(1, "Bob", "Clerk"));
        assertTrue(interactor.canStartGame());
    }

    @Test
    public void testCanStartGameTrueWhenAllPlayersAreCreated() {
        interactor.execute(new CharacterSelectionInputData(0, "Alice", "Clerk"));
        interactor.execute(new CharacterSelectionInputData(1, "Bob", "Landlord"));
        interactor.execute(new CharacterSelectionInputData(2, "Cat", "Poor Man"));
        interactor.execute(new CharacterSelectionInputData(3, "Dog", "Inheritor"));
        assertTrue(interactor.canStartGame());
    }

    @Test
    public void testCanStartGameIgnoresNullPlayers() {
        interactor.execute(new CharacterSelectionInputData(1, "Bob", "Clerk"));
        interactor.execute(new CharacterSelectionInputData(3, "Dave", "Inheritor"));
        assertTrue(interactor.canStartGame());
    }
}