# Lim Zi Jing's Project Portfolio Page

## Overview

PlayStation -1 is a keyboard-first command-line collection of short games for people who want a quick break without leaving the terminal. It now includes Wordle, Calculator, Battleship, Tic Tac Toe, GTA -6, and Snake; players can start a game from the menu and return when it ends.

## Summary of Contributions

### Code contributed

- [My contributions on the tP Code Dashboard](https://nus-cs2113-ay2627-s1.github.io/tp-dashboard/?breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&groupSelect=groupByRepos&mergegroup=&search=LimZiJing&sort=groupTitle&sortWithin=title&timeframe=commit).

### Enhancements implemented

- **Persistent points:** Added a local points store and the `score` command. Correct Calculator answers and wins in Wordle, Battleship, and Tic Tac Toe earn points saved in `~/.playstation-minus-one/points.properties`. New profiles start at zero and awards are saved immediately. Tests cover missing or invalid data, reloading totals, and score and reward flows. ([PR #118](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/118))
- **Tic Tac Toe:** Implemented coordinate validation, win and draw detection, reset behavior, and computer moves restricted to empty squares. `TicTacToeBoard` owns board rules; `TicTacToeGame` handles turns and input. An injected random source makes computer moves repeatable in tests for invalid moves, wins, draws, quitting, and replay. ([PR #104](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/104))
- **Shared game framework:** Defined the `Game` interface, game catalogue, and ID-based launch flow, then moved menu and active-game routing into `CommandHandler`. Games use `handleInput()` rather than reading the console directly, keeping launcher commands available during play; the shared lifecycle resets games for replay. ([PR #34](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/34), [PR #52](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/52), [PR #95](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/95))
- **Wordle and command reliability:** Aligned the five-guess limit across Wordle descriptions and the User Guide, with regression tests. Normalized commands with `Locale.ROOT` so commands such as `LIST` and `EXIT` work regardless of the system locale. ([PR #168](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/168), [PR #170](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/170))

### Contributions to the User Guide

- Documented the `score` command, reward rules, and persistence location, including access to saved points from the menu and during a game. ([PR #118](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/118))

### Contributions to the Developer Guide

- Wrote the initial product scope and interaction model, then simplified design sections. Added PlantUML sources and rendered diagrams for command routing, Wordle classes and guesses, and Tic Tac Toe classes and turns. ([PR #30](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/30), [PR #120](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/120))

### Contributions to team-based tasks

- Integrated games with the shared command handler so launcher commands remain available during play. Connected game rewards to the handler and common points store, keeping persistence logic out of each game. ([PR #95](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/95), [PR #118](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/118))

### Review and mentoring contributions

- Reviewed teammates' documentation and implementation. Suggested moving story dialogue into its own class and keeping PlantUML sources beside rendered diagrams in the Developer Guide. ([PR #122](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/122), [PR #121](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/121))
