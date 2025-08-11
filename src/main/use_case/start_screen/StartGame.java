package main.use_case.start_screen;

/**
 * Use case for starting the game and returning initial information.
 */
public class StartGame {
    /**
     * Executes the start‐game use case and returns the result.
     *
     * @return a {@link StartGameResult} containing the welcome message and rules text
     */
    public StartGameResult execute() {
        return new StartGameResult(
                "Welcome to our Monopoly!",
                getRulesText()
        );
    }

    /**
     * Generates the full game rules text.
     *
     * @return the rules as a multiline string
     */
    private String getRulesText() {
        return String.join(System.lineSeparator(),
                "Objective",
                "  Be the last solvent player - or, if 20 rounds pass, have the most cash.",
                "",
                "Setup",
                "  Four players start with equal cash and zero properties or stocks;",
                "  board and stock prices initialize from real-world data.",
                "",
                "Turn Sequence",
                "  1. Roll & Move: Roll two dice and advance.",
                "  2. Actions on Landing:",
                "     - Unowned Property: May buy; otherwise auction.",
                "     - Owned Property: Pay rent to the owner.",
                "     - Stock Market Tile: May buy up to five available stocks.",
                "     - Other Tiles: Follow standard Monopoly rules (Chance, Community Chest, etc.).",
                "  3. Stock Trades: At any point this turn, you may sell shares at current market price.",
                "  4. Pass Go: Collect your salary when you complete a full lap.",
                "",
                "Bankruptcy",
                "  If you can't meet a payment, you're out - give your assets to your creditor or to the bank.",
                "",
                "Stock Market",
                "  - Prices update each round based on live data.",
                "  - You can only buy when landing on the market tile; selling is always allowed.",
                "",
                "Endgame",
                "  - Instant Win: Last player standing.",
                "  - Round 20: Highest cash total (properties/stocks excluded) wins."
        );
    }

    /**
     * Value object holding the welcome message and rules.
     */
    public static class StartGameResult {
        private final String welcomeMessage;
        private final String rules;

        public StartGameResult(String welcomeMessage, String rules) {
            this.welcomeMessage = welcomeMessage;
            this.rules = rules;
        }

        public String getWelcomeMessage() {
            return welcomeMessage;
        }
        
        public String getRules() {
            return rules;
        }
    }
}
