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

    public JsonPropertyDataSource(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public List<PropertyInfo> getPropertyData() {
        try {
            InputStream inputStream = getClass().getResourceAsStream(resourcePath);
            if (inputStream == null) {
                throw new RuntimeException("Could not find file: " + resourcePath);
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
            throw new RuntimeException("Failed to load property data from " + resourcePath, e);
        }
    }

    private List<PropertyInfo> parsePropertiesFromJson(String jsonContent) {
        List<PropertyInfo> propertyData = new ArrayList<>();

        // Find the properties array
        int propertiesStart = jsonContent.indexOf("\"properties\":");
        if (propertiesStart == -1) {
            throw new RuntimeException("Properties array not found in JSON");
        }

        int arrayStart = jsonContent.indexOf("[", propertiesStart);
        int arrayEnd = jsonContent.lastIndexOf("]");

        String propertiesArray = jsonContent.substring(arrayStart + 1, arrayEnd);
        String[] propertyObjects = propertiesArray.split("\\},\\s*\\{");

        for (String propertyObj : propertyObjects) {
            propertyObj = propertyObj.replace("{", "").replace("}", "");
            String name = extractJsonValue(propertyObj, "name");
            int basePrice = Integer.parseInt(extractJsonValue(propertyObj, "basePrice"));
            propertyData.add(new PropertyInfo(name, basePrice));
        }

        return propertyData;
    }

    private String extractJsonValue(String jsonObj, String key) {
        String searchKey = "\"" + key + "\":";
        int keyStart = jsonObj.indexOf(searchKey);
        if (keyStart == -1) {
            throw new RuntimeException("Key " + key + " not found");
        }

        int valueStart = keyStart + searchKey.length();
        String remaining = jsonObj.substring(valueStart).trim();

        if (remaining.startsWith("\"")) {
            int endQuote = remaining.indexOf("\"", 1);
            return remaining.substring(1, endQuote);
        } else {
            int endComma = remaining.indexOf(",");
            if (endComma == -1) {
                return remaining.trim();
            } else {
                return remaining.substring(0, endComma).trim();
            }
        }
    }
}

