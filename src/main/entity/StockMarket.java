package main.entity;

import java.util.List;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;

public class StockMarket {
    private List<Stock> stocks;

    // TODO: This is a messy implementation, break this down according to clean architecture
    public StockMarket() {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader("Resources/StockData/stock_data.json");
            Stock[] stockArray = gson.fromJson(reader, Stock[].class);
            this.stocks = List.of(stockArray);
        } catch (IOException e) {
            e.printStackTrace();
            this.stocks = List.of(); // Fallback to an empty list on error
        }
    }
}
