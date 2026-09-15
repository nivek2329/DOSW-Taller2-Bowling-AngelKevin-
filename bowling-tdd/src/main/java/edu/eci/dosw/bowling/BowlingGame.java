package edu.eci.dosw.bowling;

import java.util.ArrayList;
import java.util.List;


public class BowlingGame {

    private final List<Frame> frames;
    private int currentFrame;

    public BowlingGame() {
        this.frames = new ArrayList<>();
        this.currentFrame = 0;
    }

   
        public void roll(int pins) {
        if (pins < 0) {
            throw new IllegalArgumentException("El numero de pinos no puede ser negativo: " + pins);
        }
    }

  
    public int score() {
        // TODO: implementar con TDD
        return 0;
    }

    public boolean isComplete() {
        // TODO: implementar con TDD
        return false;
    }

    public List<Frame> getFrames() { return List.copyOf(frames); }
}