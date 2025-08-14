package main.use_case.start_screen;

public class StartScreenOutputData {
    private final String welcomeMessage;
    private final String rules;

    public StartScreenOutputData(String welcomeMessage, String rules) {
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
