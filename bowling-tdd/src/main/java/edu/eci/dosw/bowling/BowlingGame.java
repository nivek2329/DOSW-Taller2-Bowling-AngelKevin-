package edu.eci.dosw.bowling;

import java.util.ArrayList;
import java.util.List;


public class BowlingGame {

    private static final int MIN_PINS = 0;
    private static final int MAX_PINS = 10;
    private static final int TOTAL_FRAMES = 10;

    private final List<Frame> frames;
    private int currentFrame;

    public BowlingGame() {
        this.frames = new ArrayList<>();
        this.currentFrame = 0;
    }


    public void roll(int pins) {
        if (isComplete()) {
            throw new IllegalStateException("El juego ya esta completo, no se pueden registrar mas tiros.");
        }
        validatePinRange(pins);
        if (frames.isEmpty() || frames.get(frames.size() - 1).isFull()) {
            Frame newFrame = new Frame();
            if (frames.size() == TOTAL_FRAMES - 1) {
                newFrame.setType(FrameType.TENTH);
            }
            frames.add(newFrame);
        }

        Frame current = frames.get(frames.size() - 1);

        if (current.getRolls().size() == 1) {
            int firstRoll = current.getRolls().get(0);
            if (firstRoll != MAX_PINS && firstRoll + pins > MAX_PINS) {
                throw new IllegalArgumentException(
                    "La suma de los dos tiros del frame no puede superar " + MAX_PINS);
            }
        }

        current.addRoll(pins);

        if (current.getType() != FrameType.TENTH) {
            if (current.getRolls().size() == 1 && pins == MAX_PINS) {
                current.setType(FrameType.STRIKE);
            } else if (current.getRolls().size() == 2 && current.getType() != FrameType.STRIKE) {
                int sum = current.getRolls().get(0) + current.getRolls().get(1);
                if (sum == MAX_PINS) {
                    current.setType(FrameType.SPARE);
                }
            }
        }
    }

    private void validatePinRange(int pins) {
        if (pins < MIN_PINS || pins > MAX_PINS) {
            throw new IllegalArgumentException(
                "El numero de pinos debe estar entre " + MIN_PINS + " y " + MAX_PINS + ": " + pins);
        }
    }


    public int score() {
        if (!isComplete()) {
            throw new IllegalStateException("El juego no esta completo, no se puede calcular el puntaje.");
        }
        return new BowlingScorer().calculate(frames);
    }


    public boolean isComplete() {
        return frames.size() == TOTAL_FRAMES && frames.get(TOTAL_FRAMES - 1).isFull();
    }

    public List<Frame> getFrames() { return List.copyOf(frames); }
}