## Overview

Welcome to the Battleship Game with JavaFX! This project is a classic implementation of the popular game Battleship, where players strategically place their ships on a grid and then attempt to sink their opponent's ships by guessing their coordinates.

In this version of the game, players are pitted against a bot. The game features a connection with a MySQL database to store the results of each game, allowing players to track their performance over time.

## Features

- **Interactive Gameplay:** Enjoy a user-friendly interface built with JavaFX, making it easy to place ships and make guesses on the grid.
- **Player vs Bot:** Test your strategic skills by playing against an AI-powered bot that will give you a challenging experience.
- **MySQL Database Integration:** The game connects to a MySQL database to store game results, enabling players to view their win-loss records and other statistics.

## How to Play

1. **Placement Phase:**
   - At the start of the game, you will be prompted to place your ships on the grid. Click on the cells to position your ships horizontally or vertically or use the textbox on the right.
   - Once all your ships are placed, you will switch to attack mode.

2. **Guessing Phase:**
   - The game alternates between you and the bot. Click on the opponent's grid to guess the coordinates of their ships.
   - Hits and misses will be indicated, and you'll be informed when you've sunk an entire ship.

3. **Winning the Game:**
   - The game continues until either you or the bot sinks all of the opponent's ships.
   - After the game is over, the result will be recorded in the MySQL database.

4. **Viewing Statistics:**
   - In the future, you will be able to access your game statistics from the main menu, where some relevant data are displayed.

## Installation and Setup

1. **Requirements:**
   - Java Development Kit (JDK) installed
   - MySQL functional database 

2. **Clone the Repository:**
   ```
   git clone https://github.com/alensolopes/battleshipfx
   cd battleshipfx
   ```

3. **Database Configuration:**
   - Modify the `settings.json` file with your MySQL database credentials.
   - Modify in DatabaseBuilder class line 20 : url, username, password -> Need to keep the tag "url:, username:, password:" 

4. **Compile and Run:**
   ```
   open bash and type : "java -jar BattleShipFX-1.0.jar
   or double click on jar file
   ```

## Database Structure

The MySQL database contains the following tables:

- `bs_mac_id`: Stores the mac address of the user machine to link with an uuid
  - `mac_address`
  - `bs_id`

- `user`: Stores player information
  - `bs_id`
  - `bs_pseudo`

- `game`: Stores game information
  - `game_id`
  - `game_date`
  - `bs_id` (references `user`)
  - `game_nb_boat_player` (references `user`)
  - `game_nb_boat_bot` (references `user`)
  - `game_winner` (references `user`)

## Future Enhancements

This project can be extended in several ways:

- **Relative Path:** Allow player to only modify db information in the json file instead of code file.
- **Difficulty Levels:** Implement different bot difficulty levels, each with varying levels of strategic prowess.
- **Multiplayer Mode:** Add an option to play against other human players online.
- **Enhanced Visuals:** Incorporate more visually appealing graphics and animations to enhance the gaming experience.
- **Statistics Views:** Add an option to see all the statistics directly in the game or in a web browser.
- **Customisable Game:** Incorporate a way to modify the game : number of boat, grid's size...

## Credits

This game was developed by [Alenso Lopes]. Feel free to contact me at [alenso.lopes@unicaen.fr] for any inquiries or feedback.

---

Have fun playing Battleship and may the best strategist win! 🚢🔥
