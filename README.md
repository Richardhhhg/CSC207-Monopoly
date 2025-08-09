# 💸 CSC207-Monopoly
## Summary
This repository contains the code for the final project of CSC207 for Group 20. Our goal was to improve on the popular game monopoly by adding new features in an java implementation. We chose to include a features of being able to select from different character types, each with special traits and a stock market. This makes our implementation unique and fun compared to the original monopoly while also preserving key aspects such as the interaction with properties and other players.

## 📋 Table of Contents
- [Features](#🚀Features)
- [Installation](#🛳️-How-to-Run)
- [Usage Guide](#)
- [License](#)
- [Feedback](#)
- [Contributing](#)


## 🚀 Features
- The game will allow users to enter a game of monopoly selecting from up to 4 players.
- Players will be able to choose from a list of different characters, each with different traits that give an advantage in the game.
- Our Currently Supported Basic Components of Monopoly:
  - Players can roll dice and move around the board.
  - Players can buy properties and pay rent.
  - Players can go bankrupt.
  - After making one full rotation around the board, the players will receive a set amount of money
- The Game will end when either 1 player is left not bankrupt or after 20 turns
  - At the end of 20 turns, the player with the most money wins.
- The game will have a GUI that displays the board, players, and properties.
- The game will have a stock market feature that allows players to buy and sell stocks.
  - There will be 5 predertermined real world stocks available at baseline
  - Players should be able to sell stocks at any time during their turn
  - Players will only be able to buy stocks when they land on a stock market tile
  - The price of stocks fluctuate every round based on real world stock data
  - The prices of stocks when the game starts will be based on real world stock data
- At the end of the game, an endscreen with show with the stats of players including their money (including stocks), their properties and their net value.

## 🛳️ How to Run
### Running without Use Real world Stock Data
1. Clone the repository to your local machine. This can be done either through Git Clone or by simpling downloading the zip from the dropdown menu opened by "Code":
   <img width="415" height="336" alt="image" src="https://github.com/user-attachments/assets/f4154f4a-ffa7-4f20-b3fc-dfb766b65ee2" />
2. Run the file `Main` under src/main/app

This will create a stock market using sample stocks that are not built using real world data.

### Runnning with Real World Stock Data
1. Clone the Repository follow step 1 in installing without using real stock data.
2. Obtain an API key from Alpha Vantage here: https://www.alphavantage.co/
3. Add the API key to your environment variables under the name "API_KEY". This may vary for different IDE's. Here is the process in IntelliJ:  
   a. Click the dropdown menu next to the run button and click where it reads "Edit Configurations...":  
   <img width="315" height="167" alt="image" src="https://github.com/user-attachments/assets/832dfc84-52e4-4be0-958d-7d36b8990d2d" />  
   b. In the window that opens from clicking Edit Configurations, click on "Edit Configuration Templates...":  
   <img width="811" height="696" alt="image" src="https://github.com/user-attachments/assets/06ce82b6-3468-420f-a809-1dbe105b54d0" />
   c. Click Application in the window and enter your api key in the format "API_KEY=*your api key*" into the field "Environment Variables":
   <img width="798" height="692" alt="image" src="https://github.com/user-attachments/assets/ed653480-59f9-4bf3-9699-f9da54f4715c" />

4. Go to the file `src/main/constants/Constants` and set the value for `USE_API` to `true`
5. Launch the game normally by running the file `main`.

This will build the game using stock market data sourced from real stocks. The stocks used in the game are set in the file `src/main/Resources/StockData/stock_names.json`.

### 💻 Compatibility
Supported Operating Systems
| Operating System | Tested | Works |
| -----------------|--------|-------|
| Windows          | ✔️     | ✔️   |
| Mac              | ✔️     | ✔️   |
| Linux            | ✔️     | ✖️   |

### ☕ Dependencies
**Java**: Tested with JDK 24 (Should work with JDK 16 and Up)  
**Maven Compiler**: 16  
**GSON**: 2.1.0  
**OKHTTP**: 4.12.0  
**Common Math 3**: 3.6.1  
**JSON**: 20240303

## 🎍 Feedback
We are open to feedback whether it be potential improvements or bugfixes. For bugfixes please use the [Issues Tab](https://github.com/Richardhhhg/CSC207-Monopoly/issues) and open a new issue with the label `Bug`. For improvements you are encouraged to use the [Discussions Tab](https://github.com/Richardhhhg/CSC207-Monopoly/discussions) and add any ideas you have. For a template of how to add meaning ideas to the discussion tab see the [following post](https://github.com/Richardhhhg/CSC207-Monopoly/discussions/121).

## 🚙 Contributions
Contributors:  
| Name         | Github      |
|--------------|-------------|
| David Zhao   | dtygnj123   |
| Nathan Yoon  | Nate-Yoon   |
| Richard Hong | Richardhhhg |
| Jimmy Wu     | JimmyWooHoo |

### How can you Contribute
Our project is open to improvements whether it be with code or documentation.
To contribute:
1. Make a fork of the Repository  
   a. To do this simply click the fork button:
   <img width="513" height="62" alt="image" src="https://github.com/user-attachments/assets/081ba59f-228a-4ada-bb2f-012234f78dbe" />
2. Make your changes in the fork
3. Make a Pull Request from your Fork to the Main Repository

### 🎯 Writing a Good Pull Request
In our pull requests we want a good non-technical summary of the changes implemented followed by more detailed breakdowns of how different files and modules were changed. See [PR #103](https://github.com/Richardhhhg/CSC207-Monopoly/pull/103) for a example. Expect your code to be reviewed by at least one contributor before it is approved.

### ✍️ Reviewing Contributions and Merging to Main
Aside from the overall game logic to still work without any major bugs, we expect that all code that is to be merged into main adheres to the principles of Clean Architecture and SOLID or modifies existing code to follow the principles. Thus when reviewing code, we will be looking for violations of Clean Architecture and SOLID and making necessary comments. Until these requested changes are fixed, the pull request will not be merged into main.
