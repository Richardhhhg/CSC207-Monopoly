package main.entity;

import java.util.List;
import com.google.gson.Gson;
import main.Constants.Constants;

import java.io.FileReader;
import java.io.IOException;

public class StockMarket {
    private List<Stock> stocks;

    // TODO: This is a messy implementation, break this down according to clean architecture
    // TODO: This also uses an old implementation that reads from an old file, now repreciated
    public StockMarket() {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(Constants.STOCK_DATA_FILE);
            Stock[] stockArray = gson.fromJson(reader, Stock[].class);
            this.stocks = List.of(stockArray);
        } catch (IOException e) {
            e.printStackTrace();
            this.stocks = List.of(); // Fallback to an empty list on error
        }
    }

    public List<Stock> getStocks() {
        return stocks;
    }
}
