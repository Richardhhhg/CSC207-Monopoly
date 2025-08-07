package main.interface_adapter.CharacterSelectionScreen;

public class CharacterSelectionScreenAdapterBundle {
    private final CharacterSelectionScreenController controller;
    private final CharacterSelectionScreenViewModel viewModel;

    public CharacterSelectionScreenAdapterBundle(CharacterSelectionScreenController controller,
                                                 CharacterSelectionScreenViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @return CharacterSelectionScreenController.
     */
    public CharacterSelectionScreenController getController() {
        return this.controller;
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @return CharacterSelectionScreenViewModel.
     */
    public CharacterSelectionScreenViewModel getViewModel() {
        return viewModel;
    }
}
