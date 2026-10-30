# Seow Cheng Si's Project Portfolio Page

## Overview

PlayStation -1 is a command-line collection of short games for people who want a quick break without leaving the terminal. Players can start Wordle, Calculator, Battleship, Tic Tac Toe, or GTA -6, then return to the menu when they finish.

## Summary of Contributions

- **CLI base infrastructure:** Built the foundational command-line structure — the main input loop, `Ui` class for console I/O, `Parser` for splitting user input into a command and arguments, the help command, and unknown-command handling. This established the application skeleton that all subsequent features were built on. ([PR #24](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/24))
- **List command and UX improvements:** Implemented the `list` command so players can see all available games and their IDs. Also improved the Wordle experience by showing the number of tries remaining after each wrong guess, standardised game-leaving to the `quit` command, added the quit command to the help output, and added assertions and logging across the codebase. Wrote unit tests for `GameList`, `Ui`, and `PlayStationMinusOne`. ([PR #75](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/75))
- **GTA -6 game:** Designed and implemented the full GTA -6 choose-your-own-adventure game (game ID 5). Created the `StoryNode` data class to model a branching story graph and `GtaGame` to handle player navigation through numbered choices, with multiple paths and endings. Wrote comprehensive tests for both classes. ([PR #122](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/122))
- **Code contributed:** [My contributions on the tP Code Dashboard](https://nus-cs2113-ay2627-s1.github.io/tp-dashboard/?breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&groupSelect=groupByRepos&mergegroup=&search=seowcs&sort=groupTitle&sortWithin=title&timeframe=commit).
- **Contributions to the UG:** Wrote the initial v1.0 User Guide covering the quick-start steps, all launcher commands, and the Wordle and Calculator game sections. Later added the GTA -6 game section with gameplay instructions and an example walkthrough.
- **Contributions to the DG:** Added the GTA -6 class diagram and choice-sequence diagram in PlantUML, along with the accompanying design description. Created the overall architecture diagram showing all core components and their relationships.  ([PR #125](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/125))
- **Review/mentoring contributions:** Reviewed and merged teammates' PRs, including the Calculator game ([PR #71](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/71)), CommandHandler refactor ([PR #95](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/95)), persistent points ([PR #118](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/118)), and diagram standardisation ([PR #150](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/150)).
