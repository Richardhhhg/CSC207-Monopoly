package main.interface_adapter.start_screen;

import main.use_case.start_screen.StartScreenOutputBoundary;
import main.use_case.start_screen.StartScreenOutputData;

public class StartScreenPresenter implements StartScreenOutputBoundary {
    private StartScreenViewModel viewModel;

    public StartScreenPresenter() {
        // Initialize with default empty view model
    }

    @Override
    public void presentStartScreenData(StartScreenOutputData outputData) {
        this.viewModel = new StartScreenViewModel(
                outputData.getWelcomeMessage(),
                outputData.getRules(),
                "Start Game",
                "Rules"
        );
    }

    public StartScreenViewModel getViewModel() {
        return viewModel;
    }
}
