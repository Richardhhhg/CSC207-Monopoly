package main.data_access.stock_market;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import main.constants.Config;
import main.constants.constants;
import main.use_case.stocks.StockRepository;

/**
 * This represents a version of stock repository that fetches stock information from an external API.
 */
public class ApiStockInfoRepository implements StockRepository {
    private final HttpClient httpClient;
    private final Gson gson;
    private final java.util.concurrent.ScheduledExecutorService rateLimiter =
            java.util.concurrent.Executors.newSingleThreadScheduledExecutor();

    public ApiStockInfoRepository(String apiKey) {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    /**
     * Loads ticker symbols from the JSON file.
     * @param jsonFilePath Path to the JSON file containing ticker symbols.
     * @return List of ticker symbols.
     * @throws IOException If there is an error reading the file.
     */
    public List<String> loadTickerSymbols(String jsonFilePath) throws IOException {
        final Type listType = new TypeToken<List<String>>() { }.getType();
        return gson.fromJson(new FileReader(jsonFilePath), listType);
    }

    /**
     * Retrieves stock information for a single ticker.
     * @param ticker The stock ticker symbol.
     * @return StockInfoDataOutputObject containing stock information.
     * @throws IOException If there is an error fetching data from the API.
     * @throws InterruptedException If the request is interrupted.
     */
    public StockInfoDataOutputObject getStockInfo(String ticker) throws IOException, InterruptedException {
        // Get current quote
        final double currentPrice = getCurrentPrice(ticker);

        // Get historical data for the past 5 years
        final Map<String, Double> historicalPrices = getHistoricalPrices(ticker);

        // Calculate statistics
        final List<Double> dailyReturns = calculateDailyReturns(historicalPrices);
        final double meanDailyReturn = calculateMean(dailyReturns) * constants.PERCENTAGE_MULTIPLIER;
        final double stdDev = calculateStandardDeviation(dailyReturns) * constants.PERCENTAGE_MULTIPLIER;

        return new StockInfoDataOutputObject(ticker, currentPrice, meanDailyReturn, stdDev);
    }

    /**
     * Gets current stock price using GLOBAL_QUOTE function.
     * @param ticker The stock ticker symbol.
     * @return Current stock price.
     * @throws IOException If there is an error fetching data from the API.
     * @throws InterruptedException If the request is interrupted.
     * @throws RuntimeException If the API response does not contain the expected data.
     */
    private double getCurrentPrice(String ticker) throws IOException, InterruptedException {
        final String url = String.format("%s?function=GLOBAL_QUOTE&symbol=%s&apikey=%s",
                constants.STOCK_API_URL, ticker, Config.getApiKey());

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        final JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();

        final JsonObject globalQuote = root.getAsJsonObject("Global Quote");
        if (globalQuote == null) {
            throw new RuntimeException("Failed to get current price for " + ticker);
        }

        return globalQuote.get("05. price").getAsDouble();
    }

    /**
     * Gets historical daily prices for the past 5 years.
     * @param ticker The stock ticker symbol.
     * @return Map of dates to closing prices.
     * @throws IOException If there is an error fetching data from the API.
     * @throws InterruptedException If the request is interrupted.
     * @throws RuntimeException If the API response does not contain the expected data.
     */
    private Map<String, Double> getHistoricalPrices(String ticker) throws IOException, InterruptedException {
        final String url = String.format("%s?function=TIME_SERIES_DAILY&symbol=%s&outputsize=full&apikey=%s",
                constants.STOCK_API_URL, ticker, Config.getApiKey());

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        final JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();

        final JsonObject timeSeries = root.getAsJsonObject("Time Series (Daily)");
        if (timeSeries == null) {
            throw new RuntimeException("Failed to get historical data for " + ticker);
        }

        final Map<String, Double> prices = new TreeMap<>();
        final LocalDate fiveYearsAgo = LocalDate.now().minusYears(constants.YEARS_OF_DATA);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Map.Entry<String, JsonElement> entry : timeSeries.entrySet()) {
            final String dateStr = entry.getKey();
            final LocalDate date = LocalDate.parse(dateStr, formatter);

            if (date.isAfter(fiveYearsAgo) || date.isEqual(fiveYearsAgo)) {
                final JsonObject dailyData = entry.getValue().getAsJsonObject();
                final double closePrice = dailyData.get("4. close").getAsDouble();
                prices.put(dateStr, closePrice);
            }
        }

        return prices;
    }

    /**
     * Calculates daily returns from historical prices.
     * @param prices Map of dates to closing prices.
     * @return List of daily returns as percentages.
     */
    private List<Double> calculateDailyReturns(Map<String, Double> prices) {
        final List<String> sortedDates = new ArrayList<>(prices.keySet());
        Collections.sort(sortedDates);

        final List<Double> returns = new ArrayList<>();

        for (int i = 1; i < sortedDates.size(); i++) {
            final double previousPrice = prices.get(sortedDates.get(i - 1));
            final double currentPrice = prices.get(sortedDates.get(i));
            final double dailyReturn = (currentPrice - previousPrice) / previousPrice;
            returns.add(dailyReturn);
        }

        return returns;
    }

    /**
     * Calculates mean of a list of values.
     * @param values List of values to calculate the mean.
     * @return Mean of the values.
     */
    private double calculateMean(List<Double> values) {
        return values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    /**
     * Calculates standard deviation of a list of values.
     * @param values List of values to calculate the standard deviation.
     * @return Standard deviation of the values.
     */
    private double calculateStandardDeviation(List<Double> values) {
        final double mean = calculateMean(values);
        final double variance = values.stream()
                .mapToDouble(value -> Math.pow(value - mean, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }
}
