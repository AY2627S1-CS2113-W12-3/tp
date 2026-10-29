# Vivekan Kathiravan - Project Portfolio Page

## Overview

PlayStation -1 is a collection of short terminal games designed for people who want a quick break without
leaving the command line. It includes Wordle, Calculator, Battleship, Tic Tac Toe, and GTA -6; each game
can be completed or exited within a few minutes.

## Summary of Contributions

### Code contributed

* [tP Code Dashboard](https://nus-cs2113-ay2627-s1.github.io/tp-dashboard/?search=double-vgun&breakdown=true)
* Primary implementation area: the Wordle game and its supporting command-line experience.

### Enhancements implemented

* Implemented Wordle with five-attempt gameplay, input validation, repeated-guess handling, and
  game-completion handling.
* Added colour-coded feedback showing correct-position, misplaced, and absent letters.
* Added the `list letters` command to summarise discovered letter information.
* Loaded answers from a bundled word list, expanded it with common five-letter words, and selected a
  secret word randomly for each session.
* Improved replay support by resetting Wordle state and reporting attempts used after successful completion.
* Organised Wordle classes into a dedicated package and added automated behaviour tests.

### Contributions to the User Guide

* Wrote and updated the Wordle section, including gameplay instructions, guess feedback, attempt limits,
  and `list letters`.
* Updated relevant command and help documentation for Wordle-specific commands.

### Contributions to the Developer Guide

* Documented the Wordle design and the roles of `WordleGame`, `WordleHelp`, and the word-list reader.
* Added or updated the Wordle class diagram and Wordle guess sequence diagram.
* Documented validation and feedback flow, including why invalid and repeated guesses do not consume an
  attempt.

### Contributions to team-based tasks

* Helped integrate game-specific help commands into the shared command-handling flow.
* Updated shared interfaces and UI behaviour for consistently exposing game-specific commands.
* Fixed a merge error during team integration and updated quit functionality in
  [PR #80](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/80).

### Review and mentoring contributions

* Reviewed another team's Developer Guide and provided feedback in
  [PR #21](https://github.com/NUS-CS2113-AY2627-S1/tp/pull/21).
