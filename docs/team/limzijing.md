# Lim Zi Jing's Project Portfolio Page

## Overview

PlayStation -1 is a keyboard-first command-line collection of five short games for people who want a quick break without leaving the terminal. Players can start Wordle, Calculator, Battleship, Tic Tac Toe, or GTA -6, then return to the menu when they finish.

## Summary of Contributions

### Code contributed

- [My contributions on the tP Code Dashboard](https://nus-cs2113-ay2627-s1.github.io/tp-dashboard/?breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&groupSelect=groupByRepos&mergegroup=&search=LimZiJing&sort=groupTitle&sortWithin=title&timeframe=commit).

### Enhancements implemented

- **Persistent points:** Added a local points store and the `score` command. Correct Calculator answers and wins in Wordle, Battleship, and Tic Tac Toe earn points saved in `~/.playstation-minus-one/points.properties`. A missing profile starts at zero, and new awards are saved immediately. Tests cover missing profiles, saving and reloading totals, invalid data, and the score command at the menu and during a game. ([PR #118](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/118))
- **Tic Tac Toe:** Implemented coordinate validation, win and draw detection, reset behavior, and a computer that chooses only from empty squares. `TicTacToeBoard` holds board rules and state, while `TicTacToeGame` handles turns and input. An injected random source makes computer moves repeatable in tests, which cover invalid and occupied moves, wins, draws, quitting, and replay. ([PR #104](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/104))
- **Shared game framework:** Defined the `Game` interface, game catalogue, and ID-based launch flow; moved menu and active-game routing into `CommandHandler`. Games pass input through `handleInput()` instead of reading from the console, so launcher commands remain available during a session. The shared lifecycle also resets a finished game before replay. ([PR #34](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/34), [PR #52](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/52), [PR #95](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/95))

### Contributions to the User Guide

- Documented the `score` command, reward rules, and persistence location, including that saved points are available at the menu and during a game. ([PR #118](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/118))

### Contributions to the Developer Guide

- Wrote the initial product scope and interaction model, then later simplified design sections. Added PlantUML sources and rendered diagrams: command-routing sequence, Wordle class and guess-sequence, and Tic Tac Toe class and turn-sequence. The diagrams show class responsibilities and the flow of a game launch, guess, or turn. ([PR #30](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/30), [PR #120](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/120))

### Contributions to team-based tasks

- Integrated the shared command handler with the games so launcher commands remain available during active sessions. Each game reports its award to the handler, which updates the common store; this keeps point storage out of individual game classes. ([PR #95](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/95), [PR #118](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/118))

### Review and mentoring contributions

- Reviewed teammates' documentation and implementation. Suggested moving story dialogue into its own class for better modularity and organising PlantUML sources alongside rendered diagrams in the Developer Guide. ([PR #122](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/122), [PR #121](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/121))
