package main.use_case.character_selection_screen;

/**
 * Character Lore and character traits.
 */
public class CharacterTraitsText {
    private static final String NL = System.lineSeparator();

    public CharacterTraitsText() {
    }

    /**
     * Generates the full character lore and traits text.
     *
     * @return the rules as a multiline string
     */
    public String getText() {
        return String.join(NL,
                "Inheritor:",
                "  - A naive young man who inherited $1200 from a mysterious relative. "
                        + "Pays 15% more when buying stocks and earns 15% less when selling. "
                        + "Easy money came fast--but can he keep it?",
                "",
                "Land Lord:",
                "  - A greedy landlord who starts with $1000. Gains 80% more rent, "
                        + "but struggles with stocks--pays 80% more to buy and sells for 15% less. "
                        + "Built for real estate domination, not Wall Street.",
                "",
                "Poor Man:",
                "  - Down on his luck and barely scraping by. "
                        + "Starts with only $200 and loses $20 every turn. "
                        + "Trades stocks at normal prices but faces an uphill battle to survive. "
                        + "A true underdog.",
                "",
                "Clerk:",
                "  - An ordinary office worker living paycheck to paycheck. "
                        + "Starts with $800 and earns a steady income of $20 every turn. "
                        + "No special perks or penalties--just consistency.",
                "",
                "College Student:",
                "  - A college student drowning in tuition fees, "
                        + "losing $20 every turn, but a genius with stocks--buys 15% cheaper and sells for 10% more."
        );
    }
}
