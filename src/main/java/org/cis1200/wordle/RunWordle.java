package org.cis1200.wordle;

/*
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 *
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 *
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard. The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a TicTacToe object to serve as the game's model.
 */
public class RunWordle implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Wordle");
        frame.setResizable(false);
        frame.setLocation(50, 100);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board
        GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        final JButton rules = new JButton("Game Rules");
        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JOptionPane.showMessageDialog(frame,
                        "Wordle is a game in which you must guess a 5-letter word in 6 tries. The color of \n" +
                                "the tiles will change to show how close your guess was to the final word (green\n" +
                                "means correct letter in the correct spot, yellow means correct letter in the wrong\n" +
                                "spot, and gray means that letter is not in the word at all.) This game does not\n" +
                                "include words with repeats, and thus will not allow such words as guesses.\n" +
                                "Have fun Wordling! ");
                board.requestFocusInWindow();

            }
        });
        control_panel.add(rules);

        final JButton word = new JButton("Target Word");
        word.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JOptionPane.showMessageDialog(frame,
                        "The target word is " + board.getTargetWord());
                board.requestFocusInWindow();
            }
        });

        control_panel.add(word);


        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        final JButton save= new JButton ("Save");
        File f = new File("hw09_local_temp\\files\\saveGame.txt");
        save.addActionListener(e -> board.saveGameData(f));
        control_panel.add(save);

        final JButton load= new JButton ("Load");
        File f1 = new File("hw09_local_temp\\files\\saveGame.txt");
        load.addActionListener(e -> board.readGameData(f1));
        control_panel.add(load);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }
}