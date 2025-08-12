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
import main.constants.Constants;
import main.use_case.stocks.StockRepository;

/**
 * This represents a version of stock repository that fetches stock information from an external API.
 */
public class ApiStockInfoRepository implements StockRepository {
    private final HttpClient httpClient;
    private final Gson gson;

    public ApiStockInfoRepository(String apiKey) {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    /**
     * Loads ticker symbols from the JSON file.
     *
     * @param jsonFilePath Path to the JSON file containing ticker symbols
     * @return List of ticker symbols
     * @throws IOException if the file cannot be read
     */
    public List<String> loadTickerSymbols(String jsonFilePath) throws IOException {
        final Type listType = new TypeToken<List<String>>() { }.getType();
        return gson.fromJson(new FileReader(jsonFilePath), listType);
    }

    /**
     * Retrieves stock information for a single ticker.
     *
     * @param ticker Stock ticker symbol
     * @return StockInfoDataOutputObject containing stock information
     * @throws IOException if an I/O error occurs when sending or receiving
     * @throws InterruptedException if the operation is interrupted
     */
    public StockInfoDataOutputObject getStockInfo(String ticker) throws IOException, InterruptedException {
        final double currentPrice = getCurrentPrice(ticker);

        final Map<String, Double> historicalPrices = getHistoricalPrices(ticker);

        final List<Double> dailyReturns = calculateDailyReturns(historicalPrices);
        final double meanDailyReturn = calculateMean(dailyReturns) * Constants.PERCENTAGE_MULTIPLIER;
        final double stdDev = calculateStandardDeviation(dailyReturns) * Constants.PERCENTAGE_MULTIPLIER;

        return new StockInfoDataOutputObject(ticker, currentPrice, meanDailyReturn, stdDev);
    }

    /**
     * Gets current stock price using GLOBAL_QUOTE function.
     *
     * @param ticker Stock ticker symbol
     * @return Current stock price
     * @throws IOException if an I/O error occurs when sending or receiving
     * @throws InterruptedException if the operation is interrupted
     */
    private double getCurrentPrice(String ticker) throws IOException, InterruptedException {
        final String url = String.format("%s?function=GLOBAL_QUOTE&symbol=%s&apikey=%s",
                Constants.STOCK_API_URL, ticker, Config.getApiKey());

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        final JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();

        final JsonObject globalQuote = root.getAsJsonObject("Global Quote");
        if (globalQuote == null) {
            throw new IOException("Failed to get current price for " + ticker);
        }

        return globalQuote.get("05. price").getAsDouble();
    }

    /**
     * Gets historical daily prices for the past 5 years.
     *
     * @param ticker Stock ticker symbol
     * @return Map of date strings to closing prices
     * @throws IOException if an I/O error occurs when sending or receiving
     * @throws InterruptedException if the operation is interrupted
     */
    private Map<String, Double> getHistoricalPrices(String ticker) throws IOException, InterruptedException {
        final String url = String.format("%s?function=TIME_SERIES_DAILY&symbol=%s&outputsize=full&apikey=%s",
                Constants.STOCK_API_URL, ticker, Config.getApiKey());

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        final JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();

        final JsonObject timeSeries = root.getAsJsonObject("Time Series (Daily)");
        if (timeSeries == null) {
            throw new IOException("Failed to get historical data for " + ticker);
        }

        final Map<String, Double> prices = new TreeMap<>();
        final LocalDate fiveYearsAgo = LocalDate.now().minusYears(Constants.YEARS_OF_DATA);
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
     *
     * @param prices Map of date strings to closing prices
     * @return List of daily return percentages
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
     *
     * @param values List of double values
     * @return Mean of the values
     */
    private double calculateMean(List<Double> values) {
        return values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    /**
     * Calculates standard deviation of a list of values.
     *
     * @param values List of double values
     * @return Standard deviation of the values
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
