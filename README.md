# PlayStation -1

PlayStation -1 is a lightweight collection of keyboard-driven command-line games for quick breaks in the terminal. The project keeps the experience simple: choose a game, play without leaving the shell, and return to the menu when you are finished.

## MVP

The minimum viable product contains one Wordle-style word-guessing game. Players receive feedback after each guess and can complete a short game session entirely from the command line.

## Setting up in Intellij

Prerequisites: JDK 25 (use the exact version), update Intellij to the most recent version.

1. **Ensure Intellij JDK 25 is defined as an SDK**, as described [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk) -- this step is not needed if you have used JDK 25 in a previous Intellij project.
1. **Import the project _as a Gradle project_**, as described [here](https://se-education.org/guides/tutorials/intellijImportGradleProject.html).
1. **Verify the setup**: After importing, run the application from IntelliJ or execute `./gradlew run` (macOS/Linux) or `gradlew.bat run` (Windows) in the project folder. A successful run starts the command-line application.

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

## Build automation using Gradle

* This project uses Gradle for build automation and dependency management. It includes a basic build script as well (i.e. the `build.gradle` file).
* If you are new to Gradle, refer to the [Gradle Tutorial at se-education.org/guides](https://se-education.org/guides/tutorials/gradle.html).

## Testing

### I/O redirection tests

* To run _I/O redirection_ tests (aka _Text UI tests_), navigate to the `text-ui-test` and run the `runtest(.bat/.sh)` script.

### JUnit tests

* Run the JUnit test suite with `./gradlew test` (macOS/Linux) or `gradlew.bat test` (Windows).
* If you are new to JUnit, refer to the [JUnit Tutorial at se-education.org/guides](https://se-education.org/guides/tutorials/junit.html).

## Checkstyle

* A sample CheckStyle rule configuration is provided in this project.
* If you are new to Checkstyle, refer to the [Checkstyle Tutorial at se-education.org/guides](https://se-education.org/guides/tutorials/checkstyle.html).

## CI using GitHub Actions

The project uses [GitHub actions](https://github.com/features/actions) for CI. When you push a commit to this repo or PR against it, GitHub actions will run automatically to build and verify the code as updated by the commit/PR.

## Documentation

The [`docs`](docs) folder contains the project documentation.

Steps for publishing documentation to the public: 
1. Open the repository on GitHub and select **Settings**.
1. Open **Pages** in the sidebar.
1. Set the publishing source to the branch and `/docs` folder that contain the documentation.
1. Optionally choose a theme.
