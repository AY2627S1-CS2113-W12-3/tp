# Seow Cheng Si's Project Portfolio Page

### Project: PlayStation -1

PlayStation -1 is a command-line collection of short games for people who want a quick break without leaving the terminal. Players can start Wordle, Calculator, Battleship, Tic Tac Toe, GTA -6, or Snake, then return to the menu when they finish.

Given below are my contributions to the project.

- **New Feature: CLI base infrastructure**
  - What it does: Provides the main input loop, `Ui` class for console I/O, `Parser` for splitting user input into a command and arguments, the `help` command, and unknown-command handling.
  - Justification: This established the application skeleton that all subsequent features and games were built on.
  - Highlights: Designing the single shared input loop required deciding early on that games would never read input directly, which shaped the `Game` interface contract used by the entire team.
  - PR: [#24](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/24)

- **New Feature: GTA -6 game**
  - What it does: A choose-your-own-adventure game (game ID 5) where the player navigates a branching story about a stolen car by picking numbered choices, with multiple paths and endings.
  - Justification: Adds a narrative-driven game that is structurally different from the other games, demonstrating that the `Game` interface supports diverse game types.
  - Highlights: The story is modelled as a graph of `StoryNode` objects stored in a map. Each node holds scenario text, choice labels, and keys pointing to the next nodes. Ending nodes have no choices and finish the session.
  - PR: [#122](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/122)

- **New Feature: Snake game**
  - What it does: A turn-based CLI snake game (game ID 6) where the player steers a snake using `w`/`a`/`s`/`d`/`f` to eat food and grow on a 10 × 10 ASCII board. The game ends when the snake hits a wall or itself.
  - Justification: Adds an arcade-style game that is structurally different from the existing games, adapted from real-time gameplay to the turn-based `Game` interface by advancing one step per input.
  - Highlights: The snake body is stored as a `LinkedList<int[]>` for O(1) head/tail operations. A `Direction` enum with `opposite()` prevents 180-degree reversals. `SnakeBoard.advance()` returns a `MoveResult` enum to cleanly separate board logic from game-flow decisions.

- **New Feature: `list` command**
  - What it does: Displays all available games with their IDs and descriptions so the player knows what to play.
  - PR: [#75](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/75)

- **Code contributed:** [tP Code Dashboard](https://nus-cs2113-ay2627-s1.github.io/tp-dashboard/?breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&groupSelect=groupByRepos&mergegroup=&search=seowcs&sort=groupTitle&sortWithin=title&timeframe=commit)

- **Enhancements to existing features:**
  - Improved Wordle UX to show the number of tries remaining after each wrong guess ([PR #75](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/75))
  - Standardised game-leaving to the `quit` command and listed it in the help output
  - Added assertions for game-list invariants and Wordle attempt count, and logging for game registration

- **Documentation:**
  - User Guide: Wrote the initial v1.0 User Guide covering quick-start steps, all launcher commands, and the Wordle and Calculator game sections. Later added the GTA -6 section with gameplay instructions and an example walkthrough.
  - Developer Guide: Added the GTA -6 class diagram and choice-sequence diagram in PlantUML, along with the accompanying design description. Created the overall architecture diagram showing all core components and their relationships. ([PR #125](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/125)) Added the Snake game section with a class diagram and turn-sequence diagram in PlantUML, plus manual testing instructions.

- **Community:**
  - Reviewed and merged teammates' PRs: Calculator game ([PR #71](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/71)), CommandHandler refactor ([PR #95](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/95)), persistent points ([PR #118](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/118)), diagram standardisation ([PR #150](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/150))
