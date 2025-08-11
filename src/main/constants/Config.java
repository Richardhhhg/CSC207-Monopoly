package main.constants;

public class Config {
    private static final String API_KEY;

    static {
        API_KEY = System.getenv("API_KEY");
        if (API_KEY == null) {
            throw new RuntimeException("API_KEY environment variable not set.");
        }
    }

    public static String getApiKey() {
        return API_KEY;
    }
}
