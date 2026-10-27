# Developer Guide

## Acknowledgements

Add links for any reused or adapted code, ideas, documentation, and third-party libraries.

## Design & implementation

The app reads one line at a time. `PlayStationMinusOne` owns the input loop; `CommandHandler` routes launcher commands and active-game input; `GameList` looks up games by ID; and `Ui` prints responses. Each game implements the shared `Game` interface.

### Command routing (Lim Zi Jing)

The `CommandHandler` refactor moved command routing and active-game state out of the application entry point. It parses launcher commands, selects a game for `play <gameId>`, forwards other input unchanged, and resets a game after it finishes.

![Sequence diagram showing how a game is selected](diagrams/command-routing.png)

Source: [command-routing.puml](diagrams/command-routing.puml).

### Wordle game

`WordleGame` implements `Game` as game ID `1`. It selects one answer from the bundled word list for each session. Guesses must contain five letters; malformed or repeated guesses do not use a try. `WordleHelp` provides coloured feedback and tracks hints for `list letters`.

![Wordle class diagram](diagrams/wordle-classes.png)

Source: [wordle-classes.puml](diagrams/wordle-classes.puml).

![Sequence diagram of a Wordle guess](diagrams/wordle-guess-sequence.png)

Source: [wordle-guess-sequence.puml](diagrams/wordle-guess-sequence.puml).

### Tic Tac Toe enhancement (Lim Zi Jing)

Tic Tac Toe is game ID `4`. The player uses `X`; the computer uses `O`. `TicTacToeGame` manages turns and session messages, while `TicTacToeBoard` stores the 3-by-3 grid and checks moves and outcomes. This separation keeps board rules in one class and turn flow in another.

![Tic Tac Toe class diagram](diagrams/tictactoe-classes.png)

Source: [tictactoe-classes.puml](diagrams/tictactoe-classes.puml).

A move is entered as a coordinate from `A1` to `C3`. Invalid or occupied squares leave the board unchanged and do not trigger a computer move. After a valid `X` move, the game checks for a win or draw. If the game continues, it chooses randomly from the remaining empty squares, places `O`, and checks the result again. The game shows the updated board and returns to the menu when the session ends.

![Sequence diagram of a normal Tic Tac Toe turn](diagrams/tictactoe-turn-sequence.png)

Source: [tictactoe-turn-sequence.puml](diagrams/tictactoe-turn-sequence.puml).

The computer chooses from a list of empty squares, so it cannot overwrite a mark. `TicTacToeGame` accepts an injected `Random` source, which lets tests make its moves repeatable.

### GTA -6 game (Seow Cheng Si)

GTA -6 is game ID `5`, a choose-your-own-adventure game. The player navigates a branching story by picking numbered choices. The story is modelled as a graph of `StoryNode` objects stored in a `Map<String, StoryNode>`. Each `StoryNode` holds scenario text, choice labels, and keys pointing to the next nodes. Ending nodes have no choices and finish the session.

![GTA -6 class diagram](diagrams/gta-classes.png)

Source: [gta-classes.puml](diagrams/gta-classes.puml).

When the player enters a choice number, `GtaGame.handleInput()` looks up the current node's choice keys, advances to the next node, and returns its formatted text. If the next node is an ending, the game sets itself as finished. Invalid input re-displays the current scenario without advancing.

![Sequence diagram of a GTA -6 choice](diagrams/gta-choice-sequence.png)

Source: [gta-choice-sequence.puml](diagrams/gta-choice-sequence.puml).

## Appendix A: Product Scope

PlayStation -1 is a set of short terminal games for people who want a quick break at the command line. The current games are Wordle, Calculator, Battleship, and Tic Tac Toe.

## Appendix B: User Stories

| As a ... | I want to ... | So that I can ... |
|---|---|---|
| new user | see the available games | choose one to play |
| player | start a game by ID | play without leaving the terminal |
| player | play Tic Tac Toe against the computer | enjoy a short turn-based game |
| player | quit a game | return to the launcher |

## Appendix C: Non-Functional Requirements

- The app works through keyboard input in a terminal.
- It displays a response before reading the next command.
- Invalid input produces an error instead of stopping the app.

## Appendix D: Glossary

- **CLI:** command-line interface, where the player enters commands in a terminal.
- **Game session:** one play of a game, from start until it ends or the player quits.

## Appendix E: Instructions for Manual Testing

1. Start the app and enter `list`; confirm Tic Tac Toe appears as game ID `4`.
2. Enter `play 4`; confirm the instructions and empty board appear.
3. Enter an invalid coordinate such as `Z9`, then an occupied square; confirm neither causes a computer move.
4. Enter valid coordinates until the game ends; confirm the result and updated board appear.
5. Enter `play 4` again; confirm the board is empty for the new session.