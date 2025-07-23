package main.use_case;

public class MovePlayerResult {
    private final boolean success;
    private final String errorMessage;
    private final int newPosition;
    private final boolean passedGo;

    public MovePlayerResult(boolean success, String errorMessage, int newPosition, boolean passedGo) {
        this.success = success;
        this.errorMessage = errorMessage;
        this.newPosition = newPosition;
        this.passedGo = passedGo;
    }

    // Getters
    public boolean isSuccess() { return success; }
    public String getErrorMessage() { return errorMessage; }
    public int getNewPosition() { return newPosition; }
    public boolean isPassedGo() { return passedGo; }
}