package org.cis1200.wordle;

/*
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.lang.management.GarbageCollectorMXBean;

/**
 * This class instantiates a TicTacToe object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 *
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 *
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Wordle ttt; // model for the game
    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 500;
    public static final int BOARD_HEIGHT = 600;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        ttt = new Wordle(); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        this.addKeyListener(new KeyListener() {
                                StringBuilder sb = new StringBuilder();
                                @Override
                                public void keyTyped(KeyEvent e) {
                                    if(!ttt.getGameStatus()){
                                        if(Character.isLetter(e.getKeyChar()) && sb.length() < 5){
                                            sb.append(e.getKeyChar());
                                            ttt.setBoardPosition(e.getKeyChar(), sb.length());
                                        }else if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE && sb.length() > 0){
                                            ttt.clearBoardPosition(sb.length());
                                            sb.setLength(sb.length()-1);
                                        }else if(e.getKeyChar() == KeyEvent.VK_ENTER) {
                                            if(!(sb.length() == 5 && ttt.playTurn(String.valueOf(sb)))){ //invalid word
                                                ttt.clearBoardRow();
                                            }
                                            sb.setLength(0); //reset StringBuilder
                                        }
                                    }
                                    repaint(); // repaints the game board
                                }

                                @Override
                                public void keyPressed(KeyEvent e) {

                                }

                                @Override
                                public void keyReleased(KeyEvent e) {

                                }
                            }
        );
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        ttt = new Wordle();
        status.setText("Wordle");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }


    /**
     * Draws the game board.
     *
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    public String getTargetWord() {
        return ttt.getTargetWord();
    }
    public void saveGameData(File file){
        try {
            FileOutputStream fileStream = new FileOutputStream(file);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

            objectStream.writeObject(ttt.getBoard());
            objectStream.writeObject(ttt.getBoardColors());
            objectStream.writeObject(ttt.getNumTurns());
            objectStream.writeObject(ttt.getGameStatus());
            objectStream.writeObject(ttt.getTargetWord());

            objectStream.close();
            fileStream.close();
            requestFocusInWindow();


        } catch (Exception e) {
            System.out.println("WrongSave");
        }

    }

    public void readGameData(File file){
        try {
            FileInputStream fileStream = new FileInputStream(file);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            ttt = new Wordle();

            ttt.setBoard((char[][]) objectStream.readObject());
            ttt.setBoardColors((int[][]) objectStream.readObject());
            ttt.setNumTurns((int) objectStream.readObject());
            ttt.setGameOver((boolean) objectStream.readObject());
            ttt.setTargetWord((String) objectStream.readObject());
            repaint();
            requestFocusInWindow();

            objectStream.close();
            fileStream.close();


        } catch (Exception e) {
            System.out.println("WrongLoad");
            System.out.println(e);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(new Font("Comic Sans", Font.BOLD, 32));
        // idk
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                char c = ttt.getCell(j, i);
                if (ttt.getColorCell(j,i) ==0) {
                    g.setColor(Color.white);
                }
                else if (ttt.getColorCell(j,i) == 1) {
                    g.setColor(Color.yellow);
                }
                else if (ttt.getColorCell(j,i) == 2) {
                    g.setColor(Color.green);
                }else{ // 3 means guessed
                    g.setColor(Color.gray);
                }
                g.fillRect(j*100, i*100, 100, 100); //set color before this and it works
                g.setColor(Color.black);
                g.drawString(String.valueOf(c),40 + 100 * j, 60 + 100 * i);

            }
        }


        // Draws board grid
        g.drawLine(100, 0, 100, 600);
        g.drawLine(200, 0, 200, 600);
        g.drawLine(300, 0, 300, 600);
        g.drawLine(400, 0, 400, 600);
        g.drawLine(0, 100, 600, 100);
        g.drawLine(0, 200, 600, 200);
        g.drawLine(0, 300, 600, 300);
        g.drawLine(0, 400, 600, 400);
        g.drawLine(0, 500, 600, 500);
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}

