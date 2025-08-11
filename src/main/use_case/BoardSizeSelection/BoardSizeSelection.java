package main.use_case.BoardSizeSelection;

/**
 * Use case for handling board size selection.
 * Follows Single Responsibility Principle - only handles board size logic.
 */
public class BoardSizeSelection {

    public enum BoardSize {
        SMALL(20, "Small"),
        MEDIUM(24, "Medium"),
        LARGE(28, "Large");

        private final int tileCount;
        private final String displayName;

        BoardSize(int tileCount, String displayName) {
            this.tileCount = tileCount;
            this.displayName = displayName;
        }

        public int getTileCount() {
            return tileCount;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public static class BoardSizeSelectionResult {
        private final BoardSize selectedSize;
        private final boolean isValid;
        private final String message;

        public BoardSizeSelectionResult(BoardSize selectedSize, boolean isValid, String message) {
            this.selectedSize = selectedSize;
            this.isValid = isValid;
            this.message = message;
        }

        public BoardSize getSelectedSize() {
            return selectedSize;
        }

        public boolean isValid() {
            return isValid;
        }

        public String getMessage() {
            return message;
        }
    }

    public BoardSizeSelectionResult selectBoardSize(BoardSize size) {

        return new BoardSizeSelectionResult(size, true,
            "Board size selected: " + size.getDisplayName() + " (" + size.getTileCount() + " tiles)");
    }

    public BoardSizeSelectionResult getDefaultBoardSize() {
        return new BoardSizeSelectionResult(BoardSize.MEDIUM, true, "Default board size selected");
    }
}

