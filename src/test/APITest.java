package test;

import main.Constants.Config;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

public class APITest {
    public static void main(String[] args) {
        String symbol = "AAPL"; // Example stock symbol
        String interval = "1min"; // Example interval
        String apiKey = Config.getApiKey();
        String url = String.format(
                "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=%s&interval=%s&apikey=%s",
                symbol, interval, apiKey);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                JSONObject jsonObject = new JSONObject(responseData);
                JSONObject timeSeries = jsonObject.getJSONObject("Time Series (1min)");

                // Print the latest stock data
                String latestTime = timeSeries.keys().next();
                JSONObject latestData = timeSeries.getJSONObject(latestTime);
                System.out.println("Latest stock data for " + symbol + ":");
                System.out.println("Open: " + latestData.getString("1. open"));
                System.out.println("High: " + latestData.getString("2. high"));
                System.out.println("Low: " + latestData.getString("3. low"));
                System.out.println("Close: " + latestData.getString("4. close"));
                System.out.println("Volume: " + latestData.getString("5. volume"));
            } else {
                System.err.println("Request failed: " + response.message());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
