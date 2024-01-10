=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D array: Wordle gives a player 6 tries to guess a 5 letter word.
  I store this game grid as a 2D array (6 by 5). The grid is populated with characters of the user's guesses.

  2. Collections: Originally, I planned to use collections to save the game state. I was intending to use a
  LinkedList to store each of the user's guesses. After receiving feedback, I decided to instead
  use collections to implement word verification. That is, I used File I/O to read the valid words in and add them to
  an ArrayList. Each time a user guesses a word, it is checked against this dictionary to ensure it is valid.

  3. File I/O: I used File I/O to save the game state. That is, when a user leaves the game, it will store their
  guesses when quit, then they start the next game (unless they choose to reset).

  4. JUnit Testable Component: I tested the game model using unit testing. Some things I tested were whether invalid
  guesses (words with double letters or words not in the dictionary) were registered as invalid, or if the game ended
  when the number of guesses reached 6.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
    GameBoard:  This class instantiates a TicTacToe object, which is the model for the game. As the user clicks the
    game board, the model is updated. Whenever the model is updated, the game board repaints itself and updates its
    status JLabel to reflect the current state of the model.
    RunWordle: This class sets up the top-level frame and widgets for the GUI.
    Wordle: This class is a model for Wordle. This model is completely independent of the view and controller.
    Game: This class is the main method run to start and run the game. Initializes the runnable game (Wordle)
    and runs it.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
    At first, I found it difficult to use Java Swing to print the Wordle board, and was confused why the user's input
    was registering as a guess in my model but not appearing in the GUI. After thoroughly reading the javadocs, I
    began to understand where I should be updating the board and how to separate things.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?



========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
    javadocs
    valid words text file (https://gist.github.com/dracos/dd0668f281e685bad51479e5acaadb93)
