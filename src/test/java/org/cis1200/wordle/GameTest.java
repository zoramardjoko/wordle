package org.cis1200.wordle;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest {
    //        (2) JUnit tests are being used
//                (6) Code designed to be unit-testable
//        Separation of concern
//        Model doesn’t rely on GUI components
//                (6) Test style
//        (3) Correct assertions (e.g. delta for floats, assertTrue/False, etc)
//        (3) Distinction of tests (i.e. no two tests do the same thing)
//        (2) Edge cases

    Wordle w = new Wordle();


    @Test
    public void testPlayGuess() {
        w.playTurn("helio");
        assertEquals(w.getCell(0,0),'h');
        assertEquals(w.getCell(1,0),'e');
        assertEquals(w.getCell(2,0),'l');
        assertEquals(w.getCell(3,0),'i');
        assertEquals(w.getCell(4,0),'o');
    }

    @Test
    public void testNumTurns() {
        w.playTurn("adopt");
        assertEquals(w.getNumTurns(),1);
    }
    @Test
    public void testClearRow() {
        w.playTurn("adopt");
        w.clearBoardRow();
        assertEquals(w.getCell(0,0),' ');
    }

    @Test
    public void testGameOver() {
        w.playTurn("adopt");
        w.playTurn("adopt");
        w.playTurn("adopt");
        w.playTurn("adopt");
        w.playTurn("adopt");
        w.playTurn("adopt"); // 6 guesses
        assertTrue(w.isGameOver());
        assertEquals(w.checkWinner(),2);

    }

    @Test
    public void testWin() {
        w.playTurn(w.getTargetWord());
        assertEquals(w.checkWinner(),1);
    }
}
