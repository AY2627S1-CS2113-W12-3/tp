# PlayStation -1 User Guide

## Introduction

PlayStation -1 is a collection of small games that you play by typing in a terminal. It is made for people who
spend their day at a command line and want a short break that has a clear end, without opening a
graphical game. Each game finishes in a few minutes, and you can leave any game at any time.

PlayStation -1 includes five games:

* **Wordle**: guess the secret word in 5 tries.
* **Calculator**: answer quick mental-arithmetic problems and track your score.
* **Battleship**: sink the computer's ships before it sinks yours.
* **Tic Tac Toe**: get three X marks in a row against the computer.
* **GTA -6**: navigate a branching story about a stolen car by picking choices.

* [Quick Start](#quick-start)
* [Features](#features)
  * [Viewing help: `help`](#viewing-help-help)
  * [Listing the games: `list`](#listing-the-games-list)
  * [Starting a game: `play`](#starting-a-game-play)
  * [Checking points: `score`](#checking-points-score)
  * [Leaving a game: `quit`](#leaving-a-game-quit)
  * [Exiting the app: `exit`](#exiting-the-app-exit)
  * [Game: Wordle](#game-wordle)
    * [Viewing Wordle letter hints: `list letters`](#viewing-wordle-letter-hints-list-letters)
  * [Game: Calculator](#game-calculator)
  * [Game: Battleship](#game-battleship)
    * [Viewing game boards: `board`](#viewing-boards-board)
  * [Game: Tic Tac Toe](#game-tic-tac-toe)
  * [Game: GTA -6](#game-gta--6)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

1. Make sure Java 25 is installed. To check, open a terminal and run `java -version`. The first line
   should show version `25`.
2. Download the latest `playstation-minus-one.jar` from the
   [releases page](https://github.com/AY2627S1-CS2113-W12-3/tp/releases).
3. Move the file into an empty folder, open a terminal in that folder, and run:
   ```
   java -jar playstation-minus-one.jar
   ```
4. The PlayStation -1 banner appears. Type a command and press Enter. Start with `help`, then
   `list`, then `play 1`.

## Features

**Notes about the command format:**

* Words in `UPPER_CASE` are values you supply. For example, in `play GAME_ID`, `GAME_ID` is a game's
  number, as in `play 1`.
* Commands are not case-sensitive: `help`, `HELP` and `Help` all work.
* There are two places to type:
  * **The main menu**, where you use `help`, `list`, `play`, `score` and `exit`.
  * **Inside a game**, where everything you type is sent to the game, until you `quit` or the
    game ends. The launcher commands `help`, `list`, `play`, `score` and `exit` remain available.
* In the examples below, lines starting with `>` are what you type. The app does not print the `>`.

### Viewing help: `help`

Shows the available commands. While you are playing a game, it also shows the game's commands if it has any.

Format: `help`

Example:
```
> help
Available commands:
  help  - Show this help message
  list  - Show all games and their IDs
  play <gameId>  - Start a game by its ID
  score  - Show your saved points
  quit  - Leave the current game and return to this menu
  exit  - Exit the application
```

### Listing the games: `list`

Shows every game, its description and the ID you use to start it.

Format: `list`

Example:
```
> list
Available games:

  [ID: 1] Wordle
      Guess the hidden five-letter word in five attempts

  [ID: 2] Calculator
      Solve randomly generated math equations

  [ID: 3] Battleship
      Locate and sink all enemy ships to win

  [ID: 4] Tic Tac Toe
      Get three marks in a row to win

  [ID: 5] GTA -6
      A choose-your-own-adventure game about a stolen car.
      
```

### Starting a game: `play`

Starts the game with the given ID. From then on, what you type goes to the game until it ends or you
`quit`.

Format: `play GAME_ID`

* `GAME_ID` must be a number from the `list` command.
* If `GAME_ID` is missing or is not a number, the app asks for a valid game ID and shows the list of
  games.
* If no game has that ID, the app shows the list of games so you can pick one.

Examples:
* `play 1` starts Wordle.
* `play 2` starts Calculator.
* `play 3` starts Battleship.
* `play 4` starts Tic Tac Toe.
* `play 5` starts GTA -6.

Example:
```
> play abc
Please enter a valid game ID.
Available games:

  [ID: 1] Wordle
      Guess the hidden five-letter word in five attempts

  [ID: 2] Calculator
      Solve randomly generated math equations

  [ID: 3] Battleship
      Locate and sink all enemy ships to win

  [ID: 4] Tic Tac Toe
      Get three marks in a row to win

  [ID: 5] GTA -6
      A choose-your-own-adventure game about a stolen car.
```

### Checking points: `score`

Shows your total points. This command works from the main menu and while a game is active.
Points are saved in a file in your home folder and remain available the next time you run the app.

Format: `score`

Example:
```
> score
Total points: 5
```

The app reports points after an action that earns them. The scoring rules are:

| Action | Points |
|--------|--------|
| Correct Calculator answer | 1 |
| Win Wordle | 5 |
| Win Battleship | 5 |
| Win Tic Tac Toe | 5 |
| Wrong answer, invalid input, loss, draw, or quit | 0 |

### Leaving a game: `quit`

Stops the current game and returns you to the main menu. Works in every game.

Format: `quit`

* `quit` only works inside a game. At the main menu, use `exit` to close the app.
* Calculator shows a summary of your session when you quit. See [Game: Calculator](#game-calculator).

Example:
```
> play 1
Starting Wordle...
Guess the word. You have 5 tries. Type 'quit' to leave.
> quit
Returning to the main menu.
```

### Exiting the app: `exit`

Closes PlayStation -1.

Format: `exit`

Example:
```
> exit
Bye!
```

### Game: Wordle

**ID:** 1. Start it with `play 1`.

Guess a word from the game's secret word list. You have **5 tries**.

* Type a new five-letter word and press Enter. Capital letters don't matter.
  * Invalid guesses and repeated words do not use up a try.
* After each valid guess, each letter is coloured:
  * **Green**: the letter is in the correct position.
  * **Yellow**: the letter is in the secret word, but in a different position.
  * **Red**: the letter is not in the secret word.
* After a wrong guess, the game tells you how many tries you have left.
* A correct guess wins. The game tells you how many tries you took and returns you to the main menu.
* If all 5 guesses are wrong, the game ends and returns you to the main menu.
* Type `quit` at any time to stop early.

Example of a win:
```
> play 1
Starting Wordle...
Guess the word. You have 5 tries. Type 'quit' to leave.
> cream
CREAM
Wrong guess! You have 4 tries left.
> crane
CRANE
Correct guess! You completed Wordle in 2 tries.
Returning to the main menu.
```

The letters in the example are coloured in the terminal according to the rules above.

Example of a loss:
```
Wrong guess! You have 1 try left.
> stone
Wrong guess! You are out of tries.
The word was CRANE.
Returning to the main menu.
```

#### Viewing Wordle letter hints: `list letters`

Shows the letter information discovered from your valid Wordle guesses so far.

Format: `list letters`

* This command works only while you are playing Wordle.
* Make at least one valid guess before using it.
* The display uses the same green, yellow, and red colours as the guess feedback.
  * Green letters include their one-based positions.
  * Yellow letters are known to be in the word, but their displayed positions are incorrect.
  * Red letters are not in the secret word.

Example, after guessing `cream`:
```
> list letters
Letters with correct positions:
C (position 1)
R (position 2)

Letters in incorrect positions:
E, A

Letters not in the word:
M
```

### Game: Calculator

**ID:** 2. Start it with `play 2`.

Answer mental-arithmetic problems, one at a time, for as long as you like.

* Each round shows one problem using `+`, `-`, `x` (multiply) or `/` (divide):
  * Addition and subtraction use numbers from 1 to 99. Subtraction answers are never negative.
  * Multiplication uses the 2 to 12 times tables.
  * Division always has a whole-number answer.
* Type your answer as a whole number and press Enter.
  * A correct answer shows `Correct!`. A wrong one shows the right answer.
  * Either way, the next round starts straight away.
* If you type something that isn't a whole number, such as `abc`, the round doesn't count. The same
  problem is asked again.
* Each round's header shows the round number and your score so far (correct / answered).
* Type `quit` to stop. You'll see a summary of the session, then return to the main menu.

Example (problems are random, so yours will differ):
```
> play 2
Starting Calculator...
  calculator - type 'quit' to stop.

  Round 1  |  Score: 0/0
    46 + 12 = ?
> abc
    That is not a whole number. Try again - this round does not count.
    46 + 12 = ?
> 5
    Not quite - the answer was 58.

  Round 2  |  Score: 0/1
    12 / 6 = ?
> quit
  Session summary
    Rounds attempted : 1
    Correct          : 0
    Accuracy         : 0%

  Returning to the main menu.
```

If you quit before answering any problem, accuracy shows `-` instead of a percentage.



### Game: Battleship

**ID:** 3. Start it with `play 3`.

Play Battleship against the computer. Locate and sink all four enemy ships before the computer sinks yours.

* Both players have a 7 × 7 board, with rows labelled A–G and columns numbered 1–7.
* Each board contains four randomly placed ships of lengths 4, 3, 2 and 1.
* You attack first, followed by the computer. The computer randomly selects a cell it has not previously attacked.
* The first player to sink all four opposing ships wins.

#### Attacking ships

Attacks a specified coordinate on the enemy's board.

Format: `COORDINATE`

* `COORDINATE` must be between `A1` and `G7`.
* Coordinates are case-insensitive and can contain spaces (e.g., b 4)
* Invalid or repeated coordinates do not consume a turn.

Possible attack results are `Miss.`, `Hit!` and `Hit and sunk a ship!`.

Example:

```text
> B4
Player shot at B4: Hit!
Computer shot at F2: Miss.
```

For invalid or repeated coordinates, the game displays an error message and prompts you to try again.

#### Viewing boards: `board`

Displays both boards without consuming a turn. Your ships are visible, while unhit enemy ships remain hidden.

Format: `board`

The following symbols represent the status of each cell:

| Symbol | Meaning |
|---|---|
| `S` | An unhit ship segment on your board |
| `O` | A ship segment that has been hit |
| `X` | A missed attack |
| `~` | An unknown or empty cell |

Example:

```text
> board
Your board:
   1 2 3 4 5 6 7
A  S S S S ~ ~ ~
B  ~ ~ ~ ~ ~ ~ ~
C  S S ~ ~ ~ ~ ~
D  ~ ~ ~ ~ S ~ ~
E  ~ ~ ~ ~ S ~ ~
F  S ~ ~ ~ S ~ ~
G  ~ ~ ~ ~ ~ ~ ~

Enemy's board:
   1 2 3 4 5 6 7
A  ~ ~ ~ ~ ~ ~ ~
B  ~ ~ ~ ~ ~ ~ ~
C  ~ ~ ~ ~ ~ ~ ~
D  ~ ~ ~ ~ ~ ~ ~
E  ~ ~ ~ ~ ~ ~ ~
F  ~ ~ ~ ~ ~ ~ ~
G  ~ ~ ~ ~ ~ ~ ~
```


#### Ending the game

The game ends when either player sinks all four opposing ships.  
Session statistics are displayed when you win, lose or quit Battleship.

Example:

```text
-------------------------
Rounds played: 5
Successful hits: 3
Missed shots: 2
Total shots: 5
Accuracy: 60.00%
-------------------------
Returning to the main menu.
```

All ship positions, attack records and statistics reset when a new Battleship game begins.

### Game: GTA -6

**ID:** 5. Start it with `play 5`.

A choose-your-own-adventure game. Your car has been stolen, and you must decide what to do. Each
scene describes what is happening and gives you numbered choices.

* Type the number of your choice (e.g. `1` or `2`) and press Enter.
* Each choice leads to a new scene with new choices, or to one of several endings.
* Invalid input (not a number, or out of range) re-displays the current scene.
* Type `quit` at any time to return to the main menu.
* The story has multiple paths and endings — try different choices to see them all.

Example:
```
> play 5
Starting GTA -6...
You wake up, stretch, and look out the window... YOUR CAR IS GONE!
What do you do?

  1. Call the police
  2. Go outside to investigate
> 1
You call 911. The operator asks if you saw anything suspicious last night.

  1. Mention the suspicious ice-cream truck
  2. Say you saw nothing
> 1
The police track down the ice-cream truck.
It was your neighbor Bob! He 'borrowed' your car to set up
a surprise birthday party for you.

ENDING: You get cake AND your car back. Happy birthday!
```

## FAQ

**Q**: Are my points saved when I close the app?

**A**: Yes. Your total points are saved in `.playstation-minus-one/points.properties` in your home
folder. Each game session starts fresh when you play again.

**Q**: Sometimes I see lines like `INFO: Player won Wordle in 1 try.` What are they?

**A**: These are diagnostic messages for the developers. They don't affect your game, and you can
ignore them.

**Q**: The app won't start, or `java -version` shows a version other than 25.

**A**: PlayStation -1 needs Java 25. Install Java 25, open a new terminal, and run `java -version`
again before starting the app.

## Command Summary

| Action          | Format         | Where it works |
|-----------------|----------------|----------------|
| Show help       | `help`         | Main menu      |
| List games      | `list`         | Main menu      |
| Start a game    | `play GAME_ID` | Main menu      |
| Show points     | `score`        | Main menu or inside a game |
| Leave a game    | `quit`         | Inside a game  |
| Show Wordle hints | `list letters` | Inside Wordle |
| Exit the app    | `exit`         | Main menu      |
