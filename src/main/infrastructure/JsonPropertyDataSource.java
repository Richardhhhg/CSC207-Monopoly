package main.infrastructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import main.use_case.game.PropertyDataSource;

/**
 * Infrastructure implementation for reading property data from JSON files.
 * Assumes well-formed JSON - throws exceptions for any parsing issues.
 */
public class JsonPropertyDataSource implements PropertyDataSource {
    private final String resourcePath;

    public JsonPropertyDataSource() {
        this.resourcePath = "/properties.json";
    }

    @Override
    public List<PropertyInfo> getPropertyData() {
        try {
            final String jsonContent = readJsonFile();
            return parsePropertiesFromJson(jsonContent);
        }
        catch (IOException ioException) {
            throw new RuntimeException("Failed to load property data from JSON", ioException);
        }
    }

    private String readJsonFile() throws IOException {
        final InputStream inputStream = getClass().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IOException("JSON resource not found: " + resourcePath);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            final StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            return jsonBuilder.toString();
        }
    }

    private List<PropertyInfo> parsePropertiesFromJson(String jsonContent) {
        final List<PropertyInfo> propertyData = new ArrayList<>();

        // Simple string-based parsing for well-formed JSON
        final String[] propertyBlocks = jsonContent.split("\\{");

        for (String block : propertyBlocks) {
            if (block.contains("name") && block.contains("basePrice")) {
                final String name = extractValue(block, "name");
                final String priceStr = extractValue(block, "basePrice");
                final int price = Integer.parseInt(priceStr);
                propertyData.add(new PropertyInfo(name, price));
            }
        }

        return propertyData;
    }

    private String extractValue(String block, String key) {
        final String searchPattern = "\"" + key + "\":\"";
        final int start = block.indexOf(searchPattern) + searchPattern.length();
        final int end = block.indexOf("\"", start);
        return block.substring(start, end);
    }
}
