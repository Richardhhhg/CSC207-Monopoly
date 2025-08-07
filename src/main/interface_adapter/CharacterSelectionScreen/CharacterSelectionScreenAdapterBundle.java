package main.interface_adapter.CharacterSelectionScreen;

public class CharacterSelectionScreenAdapterBundle {
    public final CharacterSelectionScreenController controller;
    public final CharacterSelectionScreenViewModel viewModel;

    public CharacterSelectionScreenAdapterBundle(CharacterSelectionScreenController controller,
                                                 CharacterSelectionScreenViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
    }
}
