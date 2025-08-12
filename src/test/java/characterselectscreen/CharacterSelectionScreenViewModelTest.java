package characterselectscreen;

import main.interface_adapter.characterSelectionScreen.CharacterSelectionScreenViewModel;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CharacterSelectionScreenViewModelTest {

    @Test
    void testSetAndGetPlayerData() {
        CharacterSelectionScreenViewModel vm = new CharacterSelectionScreenViewModel();
        // Use the use case version for input
        main.use_case.characterSelectionScreen.CharacterSelectionPlayerViewModel player =
                new main.use_case.characterSelectionScreen.CharacterSelectionPlayerViewModel("Alice", "Landlord", Color.RED, null);

        vm.setPlayerData(0, player);
        main.use_case.characterSelectionScreen.CharacterSelectionPlayerViewModel result = vm.getPlayerData(0);

        assertNotNull(result);
        assertEquals("Alice", result.getName());
        assertEquals("Landlord", result.getType());
        assertEquals(Color.RED, result.getColor());
    }

    @Test
    void testGetAllPlayersReturnsCopy() {
        CharacterSelectionScreenViewModel vm = new CharacterSelectionScreenViewModel();
        main.use_case.characterSelectionScreen.CharacterSelectionPlayerViewModel player1 =
                new main.use_case.characterSelectionScreen.CharacterSelectionPlayerViewModel("Alice", "Landlord", Color.RED, null);
        main.use_case.characterSelectionScreen.CharacterSelectionPlayerViewModel player2 =
                new main.use_case.characterSelectionScreen.CharacterSelectionPlayerViewModel("Bob", "Clerk", Color.BLUE, null);

        vm.setPlayerData(0, player1);
        vm.setPlayerData(1, player2);

        // getAllPlayers returns the adapter version
        List<main.interface_adapter.characterSelectionScreen.CharacterSelectionPlayerViewModel> all = vm.getAllPlayers();
        assertEquals(4, all.size());
        assertEquals("Alice", all.get(0).getName());
        assertEquals("Bob", all.get(1).getName());
        assertNull(all.get(2));
        assertNull(all.get(3));
    }

    @Test
    void testGetPlayervmReturnsNewInstance() {
        CharacterSelectionScreenViewModel vm = new CharacterSelectionScreenViewModel();
        main.use_case.characterSelectionScreen.CharacterSelectionPlayerViewModel player =
                new main.use_case.characterSelectionScreen.CharacterSelectionPlayerViewModel("Carol", "Merchant", Color.GREEN, null);

        vm.setPlayerData(2, player);

        main.interface_adapter.characterSelectionScreen.CharacterSelectionPlayerViewModel copy = vm.getPlayervm(2);
        assertNotSame(player, copy); // should be a new object (adapter type)
        assertEquals("Carol", copy.getName());
        assertEquals("Merchant", copy.getType());
        assertEquals(Color.GREEN, copy.getColor());
    }

    @Test
    void testGetPlayervmNullIfNoPlayer() {
        CharacterSelectionScreenViewModel vm = new CharacterSelectionScreenViewModel();
        assertNull(vm.getPlayervm(3));
    }
}