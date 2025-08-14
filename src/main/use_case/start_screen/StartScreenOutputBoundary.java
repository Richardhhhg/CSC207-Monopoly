package main.use_case.start_screen;

public interface StartScreenOutputBoundary {
    /**
     * Presents the start screen data to the view.
     *
     * @param outputData The start screen data ready for presentation.
     */
    void presentStartScreenData(StartScreenOutputData outputData);
}
