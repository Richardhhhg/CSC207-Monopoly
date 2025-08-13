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
 * Assumes well-formed JSON with known structure.
 */
public class JsonPropertyDataSource implements PropertyDataSource {
    private static final String RESOURCE_PATH = "/Board/properties.json";
    private static final String QUOTE = "\"";

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
        final InputStream inputStream = getClass().getResourceAsStream(RESOURCE_PATH);
        if (inputStream == null) {
            throw new IOException("JSON resource not found: " + RESOURCE_PATH);
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

        // Find start and end of properties array
        final int arrayStart = jsonContent.indexOf("\"properties\": [") + 15;
        final int arrayEnd = jsonContent.lastIndexOf("]");
        final String propertiesContent = jsonContent.substring(arrayStart, arrayEnd);

        // Split by property objects
        final String[] propertyBlocks = propertiesContent.split("},\\s*\\{");

        for (int i = 0; i < propertyBlocks.length; i++) {
            String block = propertyBlocks[i].trim();

            // Clean up braces
            block = block.replaceAll("^\\{|}$", "");

            final String name = extractStringValue(block);
            final int price = extractIntValue(block);
            propertyData.add(new PropertyInfo(name, price));
        }

        return propertyData;
    }

    private String extractStringValue(String block) {
        final String pattern = QUOTE + "name" + "\": " + QUOTE;
        final int start = block.indexOf(pattern) + pattern.length();
        final int end = block.indexOf(QUOTE, start);
        return block.substring(start, end);
    }

    private int extractIntValue(String block) {
        final String pattern = QUOTE + "basePrice" + "\": ";
        final int start = block.indexOf(pattern) + pattern.length();
        int end = block.indexOf(",", start);
        if (end == -1) {
            end = block.length();
        }
        return Integer.parseInt(block.substring(start, end).trim());
    }
}
