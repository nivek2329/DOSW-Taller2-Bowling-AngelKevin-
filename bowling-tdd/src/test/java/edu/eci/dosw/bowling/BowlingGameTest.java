package edu.eci.dosw.bowling;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BowlingGameTest {


    private void rollMany(BowlingGame game, int times, int pins) {
        for (int i = 0; i < times; i++) {
            game.roll(pins);
        }
    }

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

    @Test
    @DisplayName("Frame 10 con strike acepta hasta 3 tiros sin lanzar excepcion")
    void tenthFrameWithStrike_acceptsThreeRolls() {
        BowlingGame game = new BowlingGame();
        for (int i = 0; i < 18; i++) {
            game.roll(0);
        }

        assertDoesNotThrow(() -> {
            game.roll(10);
            game.roll(10);
            game.roll(10);
        });

        assertEquals(10, game.getFrames().size());
        assertEquals(3, game.getFrames().get(9).getRolls().size());
    }

    @Test
    @DisplayName("roll() lanza IllegalStateException si el juego ya esta completo")
    void rollAfterGameComplete_throwsException() {
        BowlingGame game = new BowlingGame();
        for (int i = 0; i < 20; i++) {
            game.roll(0);
        }

        assertThrows(
            IllegalStateException.class,
            () -> game.roll(0)
        );
    }


    

    @Test
    @DisplayName("isComplete() al inicio del juego es false")
    void isComplete_atStart_isFalse() {
        BowlingGame game = new BowlingGame();
        assertFalse(game.isComplete());
    }

    @Test
    @DisplayName("isComplete() despues de 9 frames completos es false")
    void isComplete_after9Frames_isFalse() {
        BowlingGame game = new BowlingGame();
        rollMany(game, 18, 0);
        assertFalse(game.isComplete());
    }

    @Test
    @DisplayName("10 frames normales completos (sin strike/spare en frame 10) es true")
    void isComplete_tenNormalFrames_isTrue() {
        BowlingGame game = new BowlingGame();
        rollMany(game, 20, 0);
        assertTrue(game.isComplete());
    }

    @Test
    @DisplayName("Spare en frame 10 con tiro bonus ejecutado es true")
    void isComplete_spareInTenthWithBonus_isTrue() {
        BowlingGame game = new BowlingGame();
        rollMany(game, 18, 0);
        game.roll(5);
        game.roll(5);
        game.roll(3);
        assertTrue(game.isComplete());
    }

    @Test
    @DisplayName("Strike en frame 10 con 2 tiros bonus ejecutados es true")
    void isComplete_strikeInTenthWithTwoBonusRolls_isTrue() {
        BowlingGame game = new BowlingGame();
        rollMany(game, 18, 0);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        assertTrue(game.isComplete());
    }

    @Test
    @DisplayName("Juego perfecto: tras el 12o strike es true")
    void isComplete_afterPerfectGame_isTrue() {
        BowlingGame game = new BowlingGame();
        rollMany(game, 12, 10);
        assertTrue(game.isComplete());
    }
}