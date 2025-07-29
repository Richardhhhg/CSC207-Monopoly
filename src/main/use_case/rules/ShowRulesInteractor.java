package main.use_case.rules;

/**
 * Interactor for the Show Rules use case.
 */
public class ShowRulesInteractor implements ShowRulesInputBoundary {
    private final ShowRulesOutputBoundary outputBoundary;

    // Exact same text as in the original StartScreen
    private static final String RULES_TEXT = """
                   Objective
                Be the last solvent player—or, if 20 rounds pass, have the most cash.
    
              Setup
                4 players start with equal cash and zero properties or stocks; board and stock prices initialize from real‑world data.
    
              Turn Sequence
                1. Roll & Move: Roll two dice and advance.
                2. Actions on Landing:
                 - Unowned Property: May buy; otherwise auction.
                 - Owned Property: Pay rent to the owner.
                 - Stock Market Tile: May buy up to 5 available stocks.
                 - Other Tiles: Follow standard Monopoly rules (Chance, Community Chest, etc.).
                3. Stock Trades: At any point this turn, you may sell shares at current market price.
                4. Pass Go: Collect your salary when you complete a full lap.
    
              Bankruptcy
                If you can’t meet a payment, you’re out—give your assets to your creditor or to the bank.
    
              Stock Market
                - Prices update each round based on live data.
                - You can only buy when landing on the market tile; selling is always allowed.
    
              Endgame
                - Instant Win: Last player standing.
                - Round 20: Highest cash total (properties/stocks excluded) wins.
            """;

    public ShowRulesInteractor(ShowRulesOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void showRules() {
        outputBoundary.presentRules(RULES_TEXT);
    }
}
