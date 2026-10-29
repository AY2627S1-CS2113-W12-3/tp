# Yue Wei An Joshua- Project Portfolio Page

## Overview
PlayStation -1 is a collection of small games that you play by typing in a terminal. It is made for people who spend their day at a command line and want a short break that has a clear end, without opening a graphical game. Each game finishes in a few minutes, and you can leave any game at any time.

### Summary of Contributions

### Code contributed

* **Code Dashboard:** [View my RepoSense contributions]([PASTE_YOUR_LONG_URL_HERE](https://nus-cs2113-ay2627-s1.github.io/tp-dashboard/?search=j-122&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-09-18T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=j-122&tabRepo=AY2627S1-CS2113-W12-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false))



### Enhancements implemented: 

**1. Battleship Game**
- Implemented a complete turn-based Battleship game where players compete against a computer on a 7 × 7 grid.


**Implementations:**
- Designed the game using `BattleshipGame`, `BattleshipBoard`, `Ship`, and `Cell` classes.
- Implemented random ship placement, attack validation, hit detection, and win/loss conditions.
- Developed a computer opponent that attacks randomly without repeating coordinates.
- Implemented session statistics, including rounds played, hits, misses, and accuracy.
- Added game reset functionality to support replayability.
- Handled edge cases such as invalid coordinates and repeated attacks without consuming turns.

**Technical challenges:** Ensuring consistent game state across multiple classes while coordinating player and computer turns, tracking ship damage, and handling game-ending conditions.


### Contributions to the User Guide

* Documented the **[Game: Battleship](UserGuide.md#game-battleship)** section, including the game's objective, rules and coordinate input format.
* Explained the `board` command and the meanings of `S`, `O`, `X` and `~`.
* Added sample player-facing outputs, invalid/repeated input behaviour, game-ending conditions and session statistics.

### Contributions to the Developer Guide

* Documented the **[Battleship game](DeveloperGuide.md#battleship-game-enhancement)** design and implementation, including class responsibilities, random ship placement, turn handling, computer target selection, game state and trade-offs.
* Created/updated UML diagrams to explain the design and key interactions:
    * [Battleship class diagram](diagrams/battleship-classes.png) — relationships between game, board, cell and ship objects; [PlantUML source](diagrams/battleship-classes.puml).
    * [Battleship attack sequence diagram](diagrams/battleship-attack-sequence.png) — interactions during a player attack and computer response; [PlantUML source](diagrams/battleship-attack-sequence.puml).

### Contributions to team-based tasks

[Add only verified contributions, e.g. integration of Battleship into the game list, team meetings, release preparation, testing, issue tracking or coordination. Include links where possible.]

### Review and mentoring contributions

[Insert PR review links and specific examples of assistance to teammates, if any. Otherwise omit this subsection.]

### Contributions beyond the project team

[Insert relevant forum posts, reports of bugs in other teams' products, or other documented contributions, if any. Otherwise omit this subsection.]

## Contributions to the Developer Guide (Extracts)

The following diagrams illustrate the code structure and a typical attack flow.

![Battleship class diagram](diagrams/battleship-classes.png)

![Battleship attack sequence diagram](diagrams/battleship-attack-sequence.png)

<!-- Keep these images only if the corresponding files are committed to docs/diagrams/. -->
