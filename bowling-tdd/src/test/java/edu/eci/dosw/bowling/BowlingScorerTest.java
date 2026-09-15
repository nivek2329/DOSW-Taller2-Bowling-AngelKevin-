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

    @Test
    @DisplayName("score() antes de completar el juego lanza IllegalStateException")
    void scoreBeforeGameComplete_throwsException() {
        BowlingGame game = new BowlingGame();
        rollMany(game, 5, 0);

        assertThrows(
            IllegalStateException.class,
            () -> game.score()
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
}