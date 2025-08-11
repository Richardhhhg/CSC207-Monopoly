package main.use_case.characterSelectionScreen;

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
                "  - A naive young man who inherited $1800 from a mysterious relative. "
                        + "Pays 10% more when buying stocks and earns 30% less when selling. "
                        + "Easy money came fast--but can he keep it?",
                "",
                "Land Lord:",
                "  - A greedy landlord who starts with $800. Gains 80% more rent, "
                        + "but struggles with stocks--pays 80% more to buy and sells for 20% less. "
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
                        + "Starts with $1200 and earns a steady income every turn. "
                        + "No special perks or penalties--just consistency.",
                "",
                "College Student:",
                "  - A college student drowning in tuition fees, "
                        + "losing $100 every turn, but a genius with stocks--buys 10% cheaper and sells for 30% more."
        );
    }
}
