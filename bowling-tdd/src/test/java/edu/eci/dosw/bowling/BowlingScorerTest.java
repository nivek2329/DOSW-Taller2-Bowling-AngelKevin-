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
}