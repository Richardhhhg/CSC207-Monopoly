package main.interface_adapter.characterSelectScreen;

import main.interface_adapter.character_selection_screen.CharacterSelectionScreenPresenter;
import main.interface_adapter.character_selection_screen.CharacterSelectionScreenViewModel;
import main.use_case.character_selection_screen.CharacterSelectionPlayerViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CharacterSelectionScreenPresenterTest {
    private CharacterSelectionScreenViewModel viewModel;
    private CharacterSelectionScreenPresenter presenter;

    @BeforeEach
    void setUp() {
        viewModel = new CharacterSelectionScreenViewModel();
        presenter = new CharacterSelectionScreenPresenter(viewModel);
    }

    @Test
    void testPreparePlayerSetsPlayerData() {
        CharacterSelectionPlayerViewModel player = new CharacterSelectionPlayerViewModel(
                "Alice", "Landlord", Color.RED, null
        );
        presenter.preparePlayer(player, 1);
        CharacterSelectionPlayerViewModel stored = viewModel.getPlayerData(1);

        assertNotNull(stored);
        assertEquals("Alice", stored.getName());
        assertEquals("Landlord", stored.getType());
        assertEquals(Color.RED, stored.getColor());
    }

    @Test
    void testPrepareLaunchDataSetsAllPlayers() {
        CharacterSelectionPlayerViewModel player1 = new CharacterSelectionPlayerViewModel(
                "Alice", "Landlord", Color.RED, null
        );
        CharacterSelectionPlayerViewModel player2 = new CharacterSelectionPlayerViewModel(
                "Bob", "Clerk", Color.BLUE, null
        );
        presenter.prepareLaunchData(Arrays.asList(player1, player2, null, null));

        assertEquals("Alice", viewModel.getPlayerData(0).getName());
        assertEquals("Bob", viewModel.getPlayerData(1).getName());
        assertNull(viewModel.getPlayerData(2));
        assertNull(viewModel.getPlayerData(3));
    }
}
