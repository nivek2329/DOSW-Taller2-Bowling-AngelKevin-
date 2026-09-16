package edu.eci.dosw.bowling;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BowlingScorerTest {

    private void rollMany(BowlingGame game, int times, int pins) {
        for (int i = 0; i < times; i++) {
            game.roll(pins);
        }
    }

    private void rollPerfectGame(BowlingGame game) {
        for (int i = 0; i < 12; i++) {
            game.roll(10);
        }
    }

   
    private void rollAllSpares(BowlingGame game, int lastBonus) {
        for (int i = 0; i < 10; i++) {
            game.roll(5);
            game.roll(5);
        }
        game.roll(lastBonus);
    }

    @Test
    @DisplayName("score() antes de completar el juego lanza IllegalStateException")
    void scoreBeforeGameComplete_throwsException() {
        BowlingGame game = new BowlingGame();
        rollMany(game, 5, 0);

        assertThrows(
    	    IllegalStateException.class,
            game::score
	);
    }

    @Test
    @DisplayName("Juego sin strikes ni spares suma directamente los pinos derribados")
    void noStrikesNoSpares_sumsAllPins() {
        BowlingGame game = new BowlingGame();
        for (int i = 0; i < 10; i++) {
            game.roll(3);
            game.roll(4);
        }

        assertEquals(70, game.score());
    }

    @Test
    @DisplayName("Juego con todos los tiros a 0 anota 0 puntos")
    void allZeros_scoresZero() {
        BowlingGame game = new BowlingGame();
        rollMany(game, 20, 0);

        assertEquals(0, game.score());
    }

    @Test
    @DisplayName("Spare en frame 1 suma el bono del primer tiro del frame 2")
    void spareInFirstFrame_addsBonusFromNextFirstRoll() {
        BowlingGame game = new BowlingGame();
        game.roll(5);
        game.roll(5); 
        game.roll(3);
        game.roll(2); 
        rollMany(game, 16, 0); 

        assertEquals(18, game.score());
    }

 
   

    @Test
    @DisplayName("Strike en frame 1 suma el bono de los dos tiros siguientes")
    void strikeInFirstFrame_addsBonusFromNextTwoRolls() {
        BowlingGame game = new BowlingGame();
        game.roll(10); 
        game.roll(4);
        game.roll(3); 
        rollMany(game, 16, 0); 

        assertEquals(24, game.score());
    }

    @Test
    @DisplayName("Dos strikes consecutivos seguidos de un tiro suman el bono correctamente")
    void twoConsecutiveStrikes_thenRoll_addsBonusCorrectly() {
        BowlingGame game = new BowlingGame();
        game.roll(10); 
        game.roll(10); 
        game.roll(5);
        game.roll(3); 
        rollMany(game, 14, 0); 

        assertEquals(51, game.score());
    }

    @Test
    @DisplayName("Todos spares con ultimo tiro de bono anota 150")
    void allSpares_withFinalBonusRoll_scores150() {
        BowlingGame game = new BowlingGame();
        rollAllSpares(game, 5);

        assertEquals(150, game.score());
    }

    @Test
    @DisplayName("Juego perfecto - 12 strikes - score debe ser 300")
    void perfectGame_scores300() {
        BowlingGame game = new BowlingGame();
        rollPerfectGame(game);

        assertEquals(300, game.score());
    }
}