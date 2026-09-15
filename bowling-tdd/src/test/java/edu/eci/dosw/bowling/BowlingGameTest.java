package edu.eci.dosw.bowling;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    @DisplayName("roll(11) lanza IllegalArgumentException")
    void rollMoreThanTenPins_throwsException() {
        BowlingGame game = new BowlingGame();

        assertThrows(
            IllegalArgumentException.class,
            () -> game.roll(11)
        );
    }

    @Test
    @DisplayName("roll(0) no lanza excepcion y el frame registra 0 pinos")
    void rollZeroPins_registersZeroInFrame() {
        BowlingGame game = new BowlingGame();

        game.roll(0);

        assertEquals(1, game.getFrames().size());
        assertEquals(0, game.getFrames().get(0).getRolls().get(0));
    }

    @Test
    @DisplayName("Dos tiros que suman mas de 10 en el mismo frame lanzan excepcion")
    void twoRollsExceedingTenInSameFrame_throwsException() {
        BowlingGame game = new BowlingGame();
        game.roll(7);

        assertThrows(
            IllegalArgumentException.class,
            () -> game.roll(6)
        );
    }

    @Test
    @DisplayName("roll(10) marca el frame como STRIKE y avanza al siguiente frame")
    void rollTenPins_marksFrameAsStrikeAndAdvances() {
        BowlingGame game = new BowlingGame();
        game.roll(10);
        game.roll(3);

        assertEquals(FrameType.STRIKE, game.getFrames().get(0).getType());
        assertEquals(2, game.getFrames().size());
    }

    @Test
    @DisplayName("roll(5) + roll(5) marca el frame como SPARE")
    void rollFivePlusFive_marksFrameAsSpare() {
        BowlingGame game = new BowlingGame();
        game.roll(5);
        game.roll(5);

        assertEquals(FrameType.SPARE, game.getFrames().get(0).getType());
    }
}