# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

The v1.0 MVP uses a small command-line interface so that each user action is
handled as one input followed by one output. The application does not use
continuous input, timers, animations, or other real-time behaviour.

### Component responsibilities

* `Duke` is the application entry point. It owns the main command loop and
  coordinates the parser, user interface, and active game.
* `Parser` converts a line of user input into a command and its arguments. For
  example, `play 1` is parsed as the command `play` with the argument `1`.
* `Ui` handles console input and output, including the startup banner, prompts,
  game feedback, and error messages.
* `Game` is the common abstraction for a playable game. It defines the
  operations needed to start a game, process a command, and leave the game.
  The v1.0 will provide one Wordle-style game through this abstraction.

### v1.0 interaction model

The application processes one complete command at a time:

1. The user enters a command.
2. `Parser` identifies the command and its arguments.
3. The active component performs the requested action.
4. `Ui` displays the result and waits for the next command.

The planned v1.0 flow is:

```text
> play 1
Starting Wordle. Enter a five-letter guess.

> guess apple
Feedback: ...

> exitgame
Returning to the main menu.
```

The command `play <gameId>` starts a game, where game `1` is the Wordle-style
game. Inside the game, `guess <word>` submits one guess and produces feedback.
The command `exitgame` leaves the active game, while `exit` closes the
application. Invalid commands display an error message without crashing the
application or consuming a guess.


## Product scope
### Target user profile

Playstation -1 is for busy individuals who work or study in a command-line
environment and want a short, clearly bounded break. It also targets retro
gaming enthusiasts and minimalists who prefer lightweight text-based games
over resource-intensive graphical games.

### Value proposition

Playstation -1 provides low-commitment CLI games that can be played in short
sessions with clear completion. Users can choose a game, interact with it using
simple commands, and receive immediate feedback without needing graphics,
animations, or a long uninterrupted session.

### v1.0 scope

The v1.0 MVP is a CLI launcher with one playable Wordle-style game. A game session
uses a fixed number of guesses, with a maximum of six valid five-letter
guesses. The session ends when the user guesses the word or uses all available
guesses.

The MVP includes:

* the existing CLI startup flow and help command;
* launching the game with `play <gameId>`;
* submitting guesses with `guess <word>`;
* feedback after every valid guess;
* error messages for invalid input; and
* returning to the launcher with `exitgame`.

The MVP deliberately excludes continuous input, timers, animations, and
background processing.

### Future features outside the MVP

The following features are deferred until after the MVP:

* additional games such as 2048, Tic-Tac-Toe, modified Tetris, and a slot
  machine;
* selectable difficulty levels;
* persistent points, scores, leaderboards, and gameplay statistics;
* tracking time spent in gameplay sessions; and
* saved settings and favourite games.

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|MVP|new user|see the available CLI commands|learn how to use the application|
|MVP|user|launch the Wordle-style game|play a short game from the command line|
|MVP|user|submit a guess and receive feedback|understand my progress after each turn|
|MVP|user|exit an active game|return to the launcher without closing the application|

## Non-Functional Requirements

* The MVP must be usable entirely through keyboard input in a terminal.
* Each user command must produce a clear output before the next command is
  read.
* Invalid commands and arguments must be handled with an error message rather
  than terminating the application unexpectedly.
* The MVP must not depend on continuous screen updates, timers, or animations.

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
