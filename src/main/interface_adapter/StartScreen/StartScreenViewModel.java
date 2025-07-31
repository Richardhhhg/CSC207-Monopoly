package main.interface_adapter.StartScreen;

public class StartScreenViewModel {
    private final String welcomeMessage;
    private final String rules;
    private final String startButtonText;
    private final String rulesButtonText;

    public StartScreenViewModel(String welcomeMessage, String rules,
                                String startButtonText, String rulesButtonText) {
        this.welcomeMessage = welcomeMessage;
        this.rules = rules;
        this.startButtonText = startButtonText;
        this.rulesButtonText = rulesButtonText;
    }

    public String getWelcomeMessage() { return welcomeMessage; }
    public String getRules() { return rules; }
    public String getStartButtonText() { return startButtonText; }
    public String getRulesButtonText() { return rulesButtonText; }
}
