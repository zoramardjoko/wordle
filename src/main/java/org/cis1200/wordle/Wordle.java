package org.cis1200.wordle;

/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * This class is a model for TicTacToe.
 *
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games. We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec36.pdf
 *
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 *
 * Run this file to see the main method play a game of TicTacToe,
 * visualized with Strings printed to the console.
 */
public class Wordle {

    private char[][] board = new char[6][5];
    private int[][] boardColors = new int[6][5]; // 0 means white, 1 means yellow, 2 is green, 3 is gray
    private int numTurns;
    private boolean gameOver;
    private String targetWord;
    private char[] splitUpTarget;
    private String guess;
    private List<String> wordList;
    private int size;

    /**
     * Constructor sets up game state.
     */
    public Wordle() {
        numTurns = 0;
        gameOver = false;
        for(int i = 0 ;i < 6; i++){
            for(int j = 0; j < 5; j++){
                board[i][j] = ' ';
            }
        }
        String filePath = "hw09_local_temp\\files\\valid-wordle-words.txt";
        File file = Paths.get(filePath).toFile();

        try (BufferedReader in = new BufferedReader(new FileReader(filePath));){
            wordList = new ArrayList<>();
            String w;
            while ((w = in.readLine()) != null) {
                if (uniqueLetters(w)) {
                    wordList.add(w);
                }
            }
            size = wordList.size();
            Random random = new Random(); // target word is adios, seed 23
            int randomInt = random.nextInt(0, size);
            targetWord = wordList.get(randomInt);
            splitUpTarget = targetWord.toCharArray();

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        reset();
    }

    public boolean uniqueLetters(String str) {
        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j < str.length(); j++) {
                if (str.charAt(i) == str.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play in a location that is
     * taken or after the game has ended. If the turn is successful and the game
     * has not ended, the player is changed. If the turn is unsuccessful or the
     * game has ended, the player is not changed.
     *
     * @param c column to play in
     * @param r row to play in
     * @return whether the turn was successful
     */


    public boolean playTurn(String h) {
        // word verification
        guess = h.substring(0, 5).toLowerCase();
        char[] splitUpGuess = guess.toCharArray();
        if (!wordList.contains(guess)) { //word verification
            return false;
        }
        else{
            for (int i = 0; i< 5; i++) {
                char c = splitUpGuess[i];
                board[numTurns][i] = c;
                if (c == splitUpTarget[i]) {
                    boardColors[numTurns][i]= 2;
                }
                else if (targetWord.contains(String.valueOf(c))) {
                    boardColors[numTurns][i]= 1;

                }
                else if (!(targetWord.contains(String.valueOf(c)))){
                    boardColors[numTurns][i]= 3;

                }
            }

            numTurns++;
            return true;
        }
    }

    public void setBoardPosition(char input, int pos){
        board[numTurns][pos-1] = Character.toLowerCase(input);
    }
    public void clearBoardPosition(int pos){
        board[numTurns][pos-1] = ' ';
    }

    public void clearBoardRow() {
        for(int i = 0; i < 5; i++){
            board[numTurns][i] = ' ';
        }
    }
    /**
     * checkWinner checks whether the game has reached a win condition.
     * checkWinner only looks for horizontal wins.
     *
     * @return 0 if nobody has won yet, 1 if player 1 has won, and 2 if player 2
     *         has won, 3 if the game hits stalemate
     *         0 = nobody won yet, 1 = win, 2 = loss
     */
    public int checkWinner() {
        // checks for word similarity
        char[] splitUpGuess = guess.toLowerCase().toCharArray();
        boolean correctGuess = true;
        for (int i = 0; i < 5; i++) {
            if (!(splitUpGuess[i] == splitUpTarget[i])) {
                correctGuess = false;
                break;
            }
        }
        if(correctGuess){
            return 1;
        }else{
            if (numTurns >= 6) { // if max number of guesses is reached
                return 2;
            } else {
                return 0;
            }
        }

    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        System.out.println("\n\nTurn " + numTurns + ":\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();

            for (int j = 0; j < boardColors[i].length; j++) {
                System.out.print(boardColors[i][j]);
            }

            System.out.println();
        }
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        board = new char[6][5];
        boardColors = new int[6][5];
        numTurns = 0;
        gameOver = false;
    }

    public char[][] getBoard() {
        return board;
    }

    public int[][] getBoardColors() {
        return boardColors;
    }

    public int getNumTurns() {
        return numTurns;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public List<String> getWordList() {
        return wordList;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public void setBoardColors(int[][] boardColors) {
        this.boardColors = boardColors;
    }

    public void setNumTurns(int numTurns) {
        this.numTurns = numTurns;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setTargetWord(String targetWord) {
        this.targetWord = targetWord;
        splitUpTarget = targetWord.toCharArray();
    }

    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board. 0 = empty, 1 = Player 1, 2 = Player 2
     */
    public char getCell(int c, int r) {
        return board[r][c];
    }

    public int getColorCell(int c, int r) {
        return boardColors[r][c];
    }

    public String getTargetWord(){
        return targetWord;
    }

    public boolean getGameStatus(){
        return gameOver;
    }

    public void setGameStatus(){
        gameOver = !gameOver;
    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        Wordle t = new Wordle();

        t.playTurn("zombi");
        t.printGameState();
        System.out.println("Winner is: " + t.checkWinner());

        t.playTurn("audio");
        t.printGameState();
        System.out.println("Winner is: " + t.checkWinner());

        t.playTurn("audio");
        t.printGameState();
        System.out.println("Winner is: " + t.checkWinner());


        t.playTurn("shear");
        t.printGameState();
        System.out.println("Winner is: " + t.checkWinner());

        t.playTurn("adios");
        t.printGameState();
        System.out.println("Winner is: " + t.checkWinner());
/*
        t.playTurn(0, 0);
        t.printGameState();

        t.playTurn(0, 2);
        t.printGameState();

        t.playTurn(2, 0);
        t.printGameState();

        t.playTurn(1, 0);
        t.printGameState();

        t.playTurn(1, 2);
        t.printGameState();

        t.playTurn(0, 1);
        t.printGameState();

        t.playTurn(2, 2);
        t.printGameState();

        t.playTurn(2, 1);
        t.printGameState();
        System.out.println();
        System.out.println();
        System.out.println("Winner is: " + t.checkWinner());

         */
    }
}
