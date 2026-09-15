package edu.eci.dosw.bowling;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BowlingGameTest {

    @Test
    @DisplayName("roll(-1) lanza IllegalArgumentException")
    void rollNegativePins_throwsException() {

        BowlingGame game = new BowlingGame();


        assertThrows(
            IllegalArgumentException.class,
            () -> game.roll(-1)
        );
    }
}