package main.use_case.BoardSizeSelection;

/**
 * Use case for handling board size selection.
 */
public class BoardSizeSelection implements BoardSizeInputBoundary {

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

        public BoardSizeSelectionResult(BoardSize selectedSize) {
            this.selectedSize = selectedSize;

        }

        public BoardSize getSelectedSize() {
            return selectedSize;
        }
    }

    @Override
    public BoardSizeSelectionResult selectBoardSize(BoardSize size) {
        return new BoardSizeSelectionResult(size);
    }

}
