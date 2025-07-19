import json
import yfinance as yf
import numpy as np

with open("StockData/stock_names.json", "r") as f:
    tickers = json.load(f)

stock_stats = []

for ticker in tickers:
    stock = yf.Ticker(ticker)
    hist = stock.history(period="6mo")  # or use '1y', 'max', etc.

    if hist.empty:
        print(f"No data for {ticker}")
        continue

    returns = hist['Close'].pct_change().dropna() * 100

    mean_return = np.mean(returns)
    std_dev = np.std(returns)

    stock_stats.append({
        "symbol": ticker,
        "mean": round(mean_return, 4),
        "stddev": round(std_dev, 4)})


with open("StockData/stock_data.json", "w") as f:
    json.dump(stock_stats, f, indent=2)
