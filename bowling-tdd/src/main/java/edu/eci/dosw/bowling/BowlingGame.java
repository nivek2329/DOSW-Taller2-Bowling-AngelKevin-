package edu.eci.dosw.bowling;

import java.util.ArrayList;
import java.util.List;

public class BowlingGame {

    private static final int MIN_PINS = 0;
    private static final int MAX_PINS = 10;

    private final List<Frame> frames;
    private int currentFrame;

    public BowlingGame() {
        this.frames = new ArrayList<>();
        this.currentFrame = 0;
    }

    public void roll(int pins) {
        validatePinRange(pins);
        if (frames.isEmpty()) {
            frames.add(new Frame());
        }
        frames.get(frames.size() - 1).addRoll(pins);
    }

    private void validatePinRange(int pins) {
        if (pins < MIN_PINS || pins > MAX_PINS) {
            throw new IllegalArgumentException(
                "El numero de pinos debe estar entre " + MIN_PINS + " y " + MAX_PINS + ": " + pins);
        }
    }

    public int score() {
        return 0;
    }

    public boolean isComplete() {
        return false;
    }

    public List<Frame> getFrames() { return List.copyOf(frames); }
}