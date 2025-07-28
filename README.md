# CSC207-Monopoly
This repository contains the code for the final project of CSC207 for Group 20. Our project is an implementation of
Monopoly in Java. Deviating from the original game, we chose to also include a stock market feature using
real life stocks and real data from stocks.

# Minimum Viable Product
- The base game will have 4 players compete in a modified game of Monopoly.
- For basic components of Monopoly, our minimum viable procuct will include:
  - Players can roll dice and move around the board.
  - Players can buy properties and pay rent.
  - Players can go bankrupt.
  - After making one full rotation around the board, the players will receive a set amount of money
- The Game will end when either 1 player is left not bankrupt or after 20 turns
  - At the end of 20 turns, the player with the most money wins. (This does not include the value of non liquidated stocks and properties)
- The game will have a GUI that displays the board, players, and properties.
- The game will have a stock market feature that allows players to buy and sell stocks.
  - There will be 5 stocks available at baseline
  - Players should be able to sell stocks at any time during their turn
  - Players will only be able to buy stocks when they land on a stock market tile
  - The price of stocks fluctuate every round based on real world stock data
  - The prices of stocks when the game starts will be based on real world stock data

# How to Run
1. Clone the repository to your local machine.
2. Run the file `Main` under src/main/app

If you wish to enable the use of the stock API for stock data, change the value of `useStockAPI` in `src/main/use_case/Initialize_Stock` to `true`.
  
Note: This project requires Java 16 or higher to run.

# Contributions
| Name         | Github      |
|--------------|-------------|
| David Zhao   | dtygnj123   |
| Nathan Yoon  | Nate-Yoon   |
| Richard Hong | Richardhhhg | 
| Jimmy Wu     | JimmyWooHoo |
