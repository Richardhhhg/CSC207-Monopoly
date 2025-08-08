package main.use_case.StartScreen;

public class StartGame {
    public StartGameResult execute() {
        // Business logic for starting the game
        return new StartGameResult(
                "Welcome to our Monopoly!",
                getRulesText()
        );
    }

    private String getRulesText() {
        return """
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
          If you can't meet a payment, you're out—give your assets to your creditor or to the bank.
    
        Stock Market
          - Prices update each round based on live data.
          - You can only buy when landing on the market tile; selling is always allowed.
    
        Endgame
          - Instant Win: Last player standing.
          - Round 20: Highest cash total (properties/stocks excluded) wins.
          
        Selectable Charaters:
         
        Inheritor:
          - A naive young man who inherited $1800 from a mysterious relative. Pays 10% more when buying stocks and earns 30% less when selling. Easy money came fast—but can he keep it?
                
        Land Lord\s
          - A greedy landlord who starts with $800. Gains 80% more rent, but struggles with stocks—pays 80% more to buy and sells for 20% less. Built for real estate domination, not Wall Street.
                
        Poor Man\s
          - Down on his luck and barely scraping by. Starts with only $200 and loses $20 every turn. Trades stocks at normal prices but faces an uphill battle to survive. A true underdog.
                
        Clerk
          - An ordinary office worker living paycheck to paycheck. Starts with $1200 and earns a steady income every turn. No special perks or penalties—just consistency.
            
        College Student\s
          - A college student drowning in tuition fees, losing $100 every turn, but a genius with stocks—buys 10% cheaper and sells for 30% more.\s
                
        """;
    }

    public static class StartGameResult {
        private final String welcomeMessage;
        private final String rules;

        public StartGameResult(String welcomeMessage, String rules) {
            this.welcomeMessage = welcomeMessage;
            this.rules = rules;
        }

        public String getWelcomeMessage() { return welcomeMessage; }
        public String getRules() { return rules; }
    }
}
