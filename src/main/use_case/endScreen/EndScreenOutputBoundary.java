package main.use_case.endScreen;

/**
 * Output boundary for the End Screen use case.
 * This interface defines how the use case interactor communicates results
 * back to the presenter layer, maintaining clean separation of concerns.
 */
public interface EndScreenOutputBoundary {

    /**
     * Presents the end game results to the view.
     *
     * @param outputData The processed end game results ready for presentation.
     */
    void presentEndGameResults(EndScreenOutputData outputData);
}
