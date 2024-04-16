# AndroidAce <img src="app/src/main/res/mipmap-hdpi/ic_launcher_round.webp" alt="AndroidAce logo" width="40">

On this page:

- [AndroidAce ](#androidace-)
  - [Project Description](#project-description)
  - [A Note on the Architecture](#a-note-on-the-architecture)
  - [Testing Procedures](#testing-procedures)

## Project Description

This app tests the user's knowledge on Android programming concepts with five multiple choice questions.
At the end of the quiz, a score is displayed, showing how many correct answers were selected.

## A Note on the Architecture

You will find that different methods are used for displaying each of the questions.
This is in keeping with the project requirements where there was to be a question showing the options as each of the following:

- buttons
- checkboxes
- radio buttons
- image views
- a list view (RecyclerView)

## Testing Procedures

You will find that only instrumented tests exist in this project. There was simply no need for any other types of tests in this project
These tests can be run using either Android Studio or the command line.

To run the instrumented test suite via the command line, enter `./gradlew connectedAndroidTest`
in the project's base directory.
