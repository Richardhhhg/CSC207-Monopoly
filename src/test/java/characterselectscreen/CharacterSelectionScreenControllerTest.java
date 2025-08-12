package characterselectscreen;

import main.interface_adapter.characterSelectionScreen.CharacterSelectionScreenController;
import main.use_case.characterSelectionScreen.CharacterSelectionInputBoundary;
import main.use_case.characterSelectionScreen.CharacterSelectionInputData;
import main.use_case.characterSelectionScreen.CharacterTraitsText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterSelectionScreenControllerTest {

    private StubInteractor stubInteractor;
    private CharacterSelectionScreenController controller;

    @BeforeEach
    void setUp() {
        stubInteractor = new StubInteractor();
        controller = new CharacterSelectionScreenController(stubInteractor);
    }

    @Test
    void selectPlayer_delegatesToInteractorWithCorrectData() {
        // given
        int index = 2;
        String name = "Alice";
        String type = "Clerk";

        // when
        controller.selectPlayer(index, name, type);

        // then
        assertNotNull(stubInteractor.lastInputData, "Interactor should receive input data");
        assertEquals(index, stubInteractor.lastInputData.getIndex());
        assertEquals(name, stubInteractor.lastInputData.getName());
        assertEquals(type, stubInteractor.lastInputData.getType());
        assertTrue(stubInteractor.executeCalled, "execute() should be called");
    }

    @Test
    void confirmSelection_callsInteractorConfirmSelection() {
        controller.confirmSelection();
        assertTrue(stubInteractor.confirmCalled, "confirmSelection() should be called");
    }

    @Test
    void canStartGame_returnsFalseWhenInteractorSaysNo() {
        stubInteractor.canStart = false;
        assertFalse(controller.canStartGame());
    }

    @Test
    void canStartGame_returnsTrueWhenInteractorSaysYes() {
        stubInteractor.canStart = true;
        assertTrue(controller.canStartGame());
    }

    @Test
    void getCharacterTraitsText_returnsLoreFromCharacterTraitsText() {
        String fromController = controller.getCharacterTraitsText();
        String fromTraits = new CharacterTraitsText().getText();

        assertNotNull(fromController);
        assertFalse(fromController.isEmpty(), "Lore text should not be empty");
        assertEquals(fromTraits, fromController, "Controller should return CharacterTraitsText content");
    }

    private static class StubInteractor implements CharacterSelectionInputBoundary {
        CharacterSelectionInputData lastInputData;
        boolean executeCalled = false;
        boolean confirmCalled = false;
        boolean canStart = false;

        @Override
        public void execute(CharacterSelectionInputData inputData) {
            this.executeCalled = true;
            this.lastInputData = inputData;
        }

        @Override
        public void confirmSelection() {
            this.confirmCalled = true;
        }

        @Override
        public boolean canStartGame() {
            return canStart;
        }
    }
}
