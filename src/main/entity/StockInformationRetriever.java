package main.entity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import main.Constants.Config;
import main.Constants.Constants;
import main.data_access.StockMarket.StockInfoDataOutputObject;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

// TODO: Refactor this into many smaller use cases
public class StockInformationRetriever {
    private final HttpClient httpClient;
    private final Gson gson;

    public StockInformationRetriever(String apiKey) {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    /**
     * Loads ticker symbols from the JSON file
     */
    public List<String> loadTickerSymbols(String jsonFilePath) throws IOException {
        Type listType = new TypeToken<List<String>>(){}.getType();
        return gson.fromJson(new FileReader(jsonFilePath), listType);
    }

    /**
     * Retrieves stock information for a single ticker
     */
    public StockInfoDataOutputObject getStockInfo(String ticker) throws IOException, InterruptedException {
        // Get current quote
        double currentPrice = getCurrentPrice(ticker);

        // Get historical data for the past 5 years
        Map<String, Double> historicalPrices = getHistoricalPrices(ticker);

        // Calculate statistics
        List<Double> dailyReturns = calculateDailyReturns(historicalPrices);
        double meanDailyReturn = calculateMean(dailyReturns) * Constants.PERCENTAGE_MULTIPLIER;
        double stdDev = calculateStandardDeviation(dailyReturns) * Constants.PERCENTAGE_MULTIPLIER;

        return new StockInfoDataOutputObject(ticker, currentPrice, meanDailyReturn, stdDev);
    }

    /**
     * Gets current stock price using GLOBAL_QUOTE function
     */
    private double getCurrentPrice(String ticker) throws IOException, InterruptedException {
        String url = String.format("%s?function=GLOBAL_QUOTE&symbol=%s&apikey=%s",
                Constants.STOCK_API_URL, ticker, Config.getApiKey());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();

        JsonObject globalQuote = root.getAsJsonObject("Global Quote");
        if (globalQuote == null) {
            throw new RuntimeException("Failed to get current price for " + ticker);
        }

        return globalQuote.get("05. price").getAsDouble();
    }

    /**
     * Gets historical daily prices for the past 5 years
     */
    private Map<String, Double> getHistoricalPrices(String ticker) throws IOException, InterruptedException {
        String url = String.format("%s?function=TIME_SERIES_DAILY&symbol=%s&outputsize=full&apikey=%s",
                Constants.STOCK_API_URL, ticker, Config.getApiKey());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();

        JsonObject timeSeries = root.getAsJsonObject("Time Series (Daily)");
        if (timeSeries == null) {
            throw new RuntimeException("Failed to get historical data for " + ticker);
        }

        Map<String, Double> prices = new TreeMap<>();
        LocalDate fiveYearsAgo = LocalDate.now().minusYears(Constants.YEARS_OF_DATA);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Map.Entry<String, JsonElement> entry : timeSeries.entrySet()) {
            String dateStr = entry.getKey();
            LocalDate date = LocalDate.parse(dateStr, formatter);

            if (date.isAfter(fiveYearsAgo) || date.isEqual(fiveYearsAgo)) {
                JsonObject dailyData = entry.getValue().getAsJsonObject();
                double closePrice = dailyData.get("4. close").getAsDouble();
                prices.put(dateStr, closePrice);
            }
        }

        return prices;
    }

    /**
     * Calculates daily returns from historical prices
     */
    private List<Double> calculateDailyReturns(Map<String, Double> prices) {
        List<String> sortedDates = new ArrayList<>(prices.keySet());
        Collections.sort(sortedDates);

        List<Double> returns = new ArrayList<>();

        for (int i = 1; i < sortedDates.size(); i++) {
            double previousPrice = prices.get(sortedDates.get(i - 1));
            double currentPrice = prices.get(sortedDates.get(i));
            double dailyReturn = (currentPrice - previousPrice) / previousPrice;
            returns.add(dailyReturn);
        }

        return returns;
    }

    /**
     * Calculates mean of a list of values
     */
    private double calculateMean(List<Double> values) {
        return values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    /**
     * Calculates standard deviation of a list of values
     */
    private double calculateStandardDeviation(List<Double> values) {
        double mean = calculateMean(values);
        double variance = values.stream()
                .mapToDouble(value -> Math.pow(value - mean, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }

    /**
     * Processes all tickers from the JSON file
     */
    public List<Stock> createStocks(String jsonFilePath) throws IOException, InterruptedException {
        List<String> tickers = loadTickerSymbols(jsonFilePath);
        List<Stock> stockInfos = new ArrayList<>();

        for (String ticker : tickers) {
            try {
                StockInfoDataOutputObject info = getStockInfo(ticker);
                Stock stock = new Stock(info);
                stockInfos.add(stock);
                System.out.println("Retrieved data for: " + ticker);

                // Note: The API has rate limit of 5 requests per minute
                // And 25 requests per day
                 Thread.sleep(Constants.API_RATE_LIMIT_DELAY_MS);

            } catch (Exception e) {
                System.err.println("Failed to retrieve data for " + ticker + ": " + e.getMessage());
            }
        }

        return stockInfos;
    }

    // Example usage
    public static void main(String[] args) {
        try {
            StockInformationRetriever retriever = new StockInformationRetriever(Config.getApiKey());

            // Get all stock information from JSON file
            List<Stock> stockInfos = retriever.createStocks(Constants.STOCK_NAME_FILE);

            // Print results
            for (Stock stock: stockInfos) {
                System.out.println(stock);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}