package main.infrastructure;

import main.use_case.Game.PropertyDataSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Infrastructure implementation for reading property data from JSON files.
 * Follows Single Responsibility Principle - only handles JSON file reading.
 */
public class JsonPropertyDataSource implements PropertyDataSource {
    private final String resourcePath;

    // Default constructor that Game class expects
    public JsonPropertyDataSource() {
        this.resourcePath = "/properties.json"; // Default path
    }

    @Override
    public List<PropertyInfo> getPropertyData() {
        try {
            InputStream inputStream = getClass().getResourceAsStream(resourcePath);
            if (inputStream == null) {
                return null; // Simple null return to trigger fallback
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();

            return parsePropertiesFromJson(jsonBuilder.toString());

        } catch (Exception e) {
            return null; // Simple null return on any error
        }
    }

    private List<PropertyInfo> parsePropertiesFromJson(String jsonContent) {
        List<PropertyInfo> propertyData = new ArrayList<>();

        // Simple parsing - if anything goes wrong, return null to trigger fallback
        int propertiesStart = jsonContent.indexOf("\"properties\":");
        if (propertiesStart == -1) return null;

        int arrayStart = jsonContent.indexOf("[", propertiesStart);
        int arrayEnd = jsonContent.lastIndexOf("]");
        if (arrayStart == -1 || arrayEnd == -1 || arrayStart >= arrayEnd) return null;

        String propertiesArray = jsonContent.substring(arrayStart + 1, arrayEnd);
        String[] propertyObjects = propertiesArray.split("\\},\\s*\\{");

        for (String propertyObj : propertyObjects) {
            propertyObj = propertyObj.replace("{", "").replace("}", "");

            try {
                String name = extractJsonValue(propertyObj, "name");
                int basePrice = Integer.parseInt(extractJsonValue(propertyObj, "basePrice"));
                propertyData.add(new PropertyInfo(name, basePrice));
            } catch (Exception e) {
                // Skip malformed entries, continue with others
            }
        }

        return propertyData.isEmpty() ? null : propertyData;
    }

    private String extractJsonValue(String jsonObj, String key) {
        String searchKey = "\"" + key + "\":";
        int keyStart = jsonObj.indexOf(searchKey);
        if (keyStart == -1) return null;

        int valueStart = keyStart + searchKey.length();
        String remaining = jsonObj.substring(valueStart).trim();

        if (remaining.startsWith("\"")) {
            int endQuote = remaining.indexOf("\"", 1);
            return endQuote == -1 ? null : remaining.substring(1, endQuote);
        } else {
            int endComma = remaining.indexOf(",");
            return endComma == -1 ? remaining.trim() : remaining.substring(0, endComma).trim();
        }
    }
}
