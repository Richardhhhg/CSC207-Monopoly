package main.infrastructure;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import main.use_case.game.PropertyDataSource;

class JsonPropertyDataSourceTest {

    private JsonPropertyDataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = new JsonPropertyDataSource();
    }

    @Test
    void testGetPropertyData_ValidJson_ReturnsPropertyList() {
        // Test with the actual resource file if it exists
        // This tests the happy path
        assertDoesNotThrow(() -> {
            List<PropertyDataSource.PropertyInfo> properties = dataSource.getPropertyData();
            assertNotNull(properties);
        });
    }

    @Test
    void testGetPropertyData_ResourceNotFound_ThrowsRuntimeException() {
        // Create a subclass to override getClass().getResourceAsStream() behavior
        JsonPropertyDataSource testDataSource = new JsonPropertyDataSource() {
            @Override
            protected InputStream getResourceStream() {
                return null; // Simulate resource not found
            }
        };

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> testDataSource.getPropertyData());

        assertTrue(exception.getMessage().contains("Failed to load property data from JSON"));
        assertInstanceOf(IOException.class, exception.getCause());
        assertTrue(exception.getCause().getMessage().contains("JSON resource not found"));
    }

    @Test
    void testGetPropertyData_IoExceptionDuringRead_ThrowsRuntimeException() {
        // Create a custom InputStream that throws IOException when read
        InputStream failingInputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("Simulated read failure");
            }
        };

        JsonPropertyDataSource testDataSource = new JsonPropertyDataSource() {
            @Override
            protected InputStream getResourceStream() {
                return failingInputStream;
            }
        };

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> testDataSource.getPropertyData());

        assertTrue(exception.getMessage().contains("Failed to load property data from JSON"));
        assertInstanceOf(IOException.class, exception.getCause());
    }

    @Test
    void testParsePropertiesFromJson_ValidFormat_ParsesCorrectly() {
        String validJson = "{\"properties\": [{\"name\": \"Test Property\", \"basePrice\": 100}]}";

        JsonPropertyDataSource testDataSource = new JsonPropertyDataSource() {
            @Override
            protected InputStream getResourceStream() {
                return new ByteArrayInputStream(validJson.getBytes());
            }
        };

        List<PropertyDataSource.PropertyInfo> properties = testDataSource.getPropertyData();

        assertEquals(1, properties.size());
        assertEquals("Test Property", properties.get(0).getName());
        assertEquals(100, properties.get(0).getBasePrice());
    }

    @Test
    void testParsePropertiesFromJson_MultipleProperties_ParsesAll() {
        String multiPropertyJson = "{\"properties\": [" +
            "{\"name\": \"Property 1\", \"basePrice\": 100}," +
            "{\"name\": \"Property 2\", \"basePrice\": 200}" +
            "]}";

        JsonPropertyDataSource testDataSource = new JsonPropertyDataSource() {
            @Override
            protected InputStream getResourceStream() {
                return new ByteArrayInputStream(multiPropertyJson.getBytes());
            }
        };

        List<PropertyDataSource.PropertyInfo> properties = testDataSource.getPropertyData();

        assertEquals(2, properties.size());
        assertEquals("Property 1", properties.get(0).getName());
        assertEquals(100, properties.get(0).getBasePrice());
        assertEquals("Property 2", properties.get(1).getName());
        assertEquals(200, properties.get(1).getBasePrice());
    }

    @Test
    void testParsePropertiesFromJson_PropertyWithoutComma_ParsesCorrectly() {
        // Test the edge case where end = block.length() in extractIntValue
        String singlePropertyJson = "{\"properties\": [{\"name\": \"Last Property\", \"basePrice\": 300}]}";

        JsonPropertyDataSource testDataSource = new JsonPropertyDataSource() {
            @Override
            protected InputStream getResourceStream() {
                return new ByteArrayInputStream(singlePropertyJson.getBytes());
            }
        };

        List<PropertyDataSource.PropertyInfo> properties = testDataSource.getPropertyData();

        assertEquals(1, properties.size());
        assertEquals("Last Property", properties.get(0).getName());
        assertEquals(300, properties.get(0).getBasePrice());
    }

    @Test
    void testParsePropertiesFromJson_MalformedJson_ThrowsNumberFormatException() {
        String malformedJson = "{\"properties\": [{\"name\": \"Test\", \"basePrice\": \"not_a_number\"}]}";

        JsonPropertyDataSource testDataSource = new JsonPropertyDataSource() {
            @Override
            protected InputStream getResourceStream() {
                return new ByteArrayInputStream(malformedJson.getBytes());
            }
        };

        assertThrows(NumberFormatException.class, () -> testDataSource.getPropertyData());
    }

    @Test
    void testParsePropertiesFromJson_PropertyWithSpaces_ParsesCorrectly() {
        String jsonWithSpaces = "{\"properties\": [  {  \"name\": \"Spaced Property\"  ,  \"basePrice\": 150  }  ]}";

        JsonPropertyDataSource testDataSource = new JsonPropertyDataSource() {
            @Override
            protected InputStream getResourceStream() {
                return new ByteArrayInputStream(jsonWithSpaces.getBytes());
            }
        };

        List<PropertyDataSource.PropertyInfo> properties = testDataSource.getPropertyData();

        assertEquals(1, properties.size());
        assertEquals("Spaced Property", properties.get(0).getName());
        assertEquals(150, properties.get(0).getBasePrice());
    }

    @Test
    void testParsePropertiesFromJson_InvalidJsonStructure_ThrowsStringIndexOutOfBounds() {
        String invalidJson = "{\"notProperties\": []}";

        JsonPropertyDataSource testDataSource = new JsonPropertyDataSource() {
            @Override
            protected InputStream getResourceStream() {
                return new ByteArrayInputStream(invalidJson.getBytes());
            }
        };

        assertThrows(StringIndexOutOfBoundsException.class, () -> testDataSource.getPropertyData());
    }

    @Test
    void testParsePropertiesFromJson_EmptyPropertyName_ParsesCorrectly() {
        String emptyNameJson = "{\"properties\": [{\"name\": \"\", \"basePrice\": 100}]}";

        JsonPropertyDataSource testDataSource = new JsonPropertyDataSource() {
            @Override
            protected InputStream getResourceStream() {
                return new ByteArrayInputStream(emptyNameJson.getBytes());
            }
        };

        List<PropertyDataSource.PropertyInfo> properties = testDataSource.getPropertyData();

        assertEquals(1, properties.size());
        assertEquals("", properties.get(0).getName());
        assertEquals(100, properties.get(0).getBasePrice());
    }

    @Test
    void testParsePropertiesFromJson_NegativePrice_ParsesCorrectly() {
        String negativePriceJson = "{\"properties\": [{\"name\": \"Negative Property\", \"basePrice\": -50}]}";

        JsonPropertyDataSource testDataSource = new JsonPropertyDataSource() {
            @Override
            protected InputStream getResourceStream() {
                return new ByteArrayInputStream(negativePriceJson.getBytes());
            }
        };

        List<PropertyDataSource.PropertyInfo> properties = testDataSource.getPropertyData();

        assertEquals(1, properties.size());
        assertEquals("Negative Property", properties.get(0).getName());
        assertEquals(-50, properties.get(0).getBasePrice());
    }

    @Test
    void testParsePropertiesFromJson_ZeroPrice_ParsesCorrectly() {
        String zeroPriceJson = "{\"properties\": [{\"name\": \"Free Property\", \"basePrice\": 0}]}";

        JsonPropertyDataSource testDataSource = new JsonPropertyDataSource() {
            @Override
            protected InputStream getResourceStream() {
                return new ByteArrayInputStream(zeroPriceJson.getBytes());
            }
        };

        List<PropertyDataSource.PropertyInfo> properties = testDataSource.getPropertyData();

        assertEquals(1, properties.size());
        assertEquals("Free Property", properties.get(0).getName());
        assertEquals(0, properties.get(0).getBasePrice());
    }
}
