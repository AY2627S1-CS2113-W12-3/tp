# Poonnat Wattanakul - Project Portfolio Page

## Overview

PlayStation -1 is a command-line collection of five short games for people who want a quick break without
leaving the terminal. Players can start Wordle, Calculator, Battleship, Tic Tac Toe, or GTA -6 from a single
shared prompt, and every launcher command stays reachable while a game is in progress.

## Summary of Contributions

### Code contributed

* [tP Code Dashboard](https://nus-cs2113-ay2627-s1.github.io/tp-dashboard/?breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&groupSelect=groupByRepos&mergegroup=&search=PoonnatW&sort=groupTitle&sortWithin=title&timeframe=commit)
* Primary implementation areas: the Calculator game, the top-level command loop, and the Developer Guide
  diagrams.

### Enhancements implemented

* **Calculator game (game ID `2`):** Implemented a mental-arithmetic drill that generates one problem per
  round and grades a single line of input. `ArithmeticProblem.generate()` builds the problem and stores its
  own answer so the game never recomputes it, and division problems are built from a quotient and a divisor
  so the answer is always a whole number. Input that is not a whole number is not treated as an attempt —
  the counters and the current problem are left unchanged and the round is re-asked — so a typo cannot cost
  the player a point. `quit` ends the session and reports rounds attempted, number correct, and accuracy.
  Covered by 18 unit tests across `ArithmeticProblemTest` and `CalculatorGameTest`, including operand
  ranges, whole-number division, non-numeric and negative input, case-insensitive `quit`, accuracy
  rounding, and reset behaviour.
  ([PR #71](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/71))

* **Single top-level command loop:** Fixed a mode lock in `PlayStationMinusOne`. Each game had previously
  been run to completion inside a nested loop, so while that loop was running it owned the input stream and
  `help`, `play`, and `exit` were all unreachable until the game ended. This was invisible while Wordle was
  the only game, because `WordleGame` finished on every input and the nested loop always ran exactly one
  iteration; the Calculator runs until the player leaves, which turned the latent lock into a real one. The
  active game became a variable in the single remaining loop, with each line checked against the launcher
  commands before it reaches the game. This is the behaviour the team confirmed with our tutor: the terminal
  must never switch to a separate "page".
  ([PR #72](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/72))

* **`Game.getStartMessage()`:** Added so a game can show its opening prompt before the player has typed
  anything, which the Calculator needs because the shared loop reads a line before calling into the game.
  Declared as a `default` method returning `""`, so `WordleGame` and the other existing games compiled
  untouched rather than each needing an empty override.
  ([PR #71](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/71))

### Contributions to the User Guide

* Corrected the Wordle sample output, which still showed `You got it in` after the code had been changed to
  print `You completed Wordle in`.
  ([PR #150](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/150))

### Contributions to the Developer Guide

* **Calculator section:** Wrote the Calculator design section with a class diagram and a round sequence
  diagram, placed in game-ID order to match the surrounding sections. The prose records the two decisions
  that are not obvious from the code: why `ArithmeticProblem` stores its own answer and constructs division
  problems from a quotient and divisor, and why `CalculatorGame` keeps round state in fields instead of
  looping.
  ([PR #134](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/134))

* **Standardised all 11 diagrams to course notation:** Replaced PlantUML's default type-icon circles with
  `<<interface>>` / `<<enumeration>>` stereotype text, split attributes and operations into separate
  compartments, moved multiplicities to the association ends, added a direction marker to every association
  label, spelled out method parameters instead of abstracting them to `(...)`, renamed sequence lifelines to
  `:ClassName`, and switched a single-branch conditional from `alt` to `opt`. Extracted the shared settings
  into `docs/diagrams/style.iuml`, so a future style change is one file rather than eleven.
  ([PR #150](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/150))

### Contributions to team-based tasks

* Broke the Calculator work into ten tracked issues ([#61](https://github.com/AY2627S1-CS2113-W12-3/tp/issues/61)–[#70](https://github.com/AY2627S1-CS2113-W12-3/tp/issues/70))
  so the feature could be reviewed in reasonable steps rather than as one large change.
* The command-loop fix was a shared-component change rather than a Calculator one: it is what keeps
  launcher commands available inside every game, and it defined the `Game` contract the other games follow.
* Fixed two documentation defects found while standardising the diagrams: GTA -6's two PNGs had never been
  generated, so the published guide was showing broken image links, and `battleship-classes.png` had
  drifted from its own `.puml` source. Made every image reproducible from its source with one command
  (`java -jar .tools/plantuml.jar docs/diagrams/*.puml`) so that drift cannot silently recur, with the
  rendering jar gitignored rather than committed.
* Fixed a broken portfolio link in `AboutUs.md` that pointed at a filename from before a rename.

### Review and mentoring contributions

* Addressed the five diagram issues raised by an external PE tester
  ([#128](https://github.com/AY2627S1-CS2113-W12-3/tp/issues/128)–[#132](https://github.com/AY2627S1-CS2113-W12-3/tp/issues/132)),
  which required editing diagrams authored by three different teammates. Shared before-and-after images of
  all 11 diagrams with the team so the visual changes were reviewable without reading the PlantUML diff, and
  deliberately kept the style change in its own PR, separate from the Calculator diagrams it followed
  ([PR #134](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/134) then
  [PR #150](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/150)), so teammates were not asked to review
  new content and restyled content together.
* Deliberately left the architecture and object diagram work ([#127](https://github.com/AY2627S1-CS2113-W12-3/tp/issues/127))
  out of the standardisation PR and proposed in the team chat that a teammate pick it up. It was new design
  work rather than restyling, so it did not belong in a style-only PR — but the larger reason was that
  absorbing it would have taken a substantial documentation contribution away from someone else when I had
  already authored or rewritten every diagram in the guide. A teammate took it on and delivered it in
  [PR #153](https://github.com/AY2627S1-CS2113-W12-3/tp/pull/153).
