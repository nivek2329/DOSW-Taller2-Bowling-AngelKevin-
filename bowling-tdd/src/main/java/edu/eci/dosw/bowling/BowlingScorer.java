package edu.eci.dosw.bowling;

import java.util.ArrayList;
import java.util.List;


public class BowlingScorer {

    private static final int MAX_PINS = 10;


    public int calculate(List<Frame> frames) {
        List<Integer> rolls = flatten(frames);

        int total = 0;
        int rollIndex = 0;
        for (int frameNumber = 0; frameNumber < frames.size(); frameNumber++) {
            if (isStrike(rolls, rollIndex)) {
                total += MAX_PINS + rolls.get(rollIndex + 1) + rolls.get(rollIndex + 2);
                rollIndex += 1;
            } else if (isSpare(rolls, rollIndex)) {
                total += MAX_PINS + rolls.get(rollIndex + 2);
                rollIndex += 2;
            } else {
                total += rolls.get(rollIndex) + rolls.get(rollIndex + 1);
                rollIndex += 2;
            }
        }
        return total;
    }

    private List<Integer> flatten(List<Frame> frames) {
        List<Integer> rolls = new ArrayList<>();
        for (Frame frame : frames) {
            rolls.addAll(frame.getRolls());
        }
        return rolls;
    }

    private boolean isStrike(List<Integer> rolls, int rollIndex) {
        return rolls.get(rollIndex) == MAX_PINS;
    }

    private boolean isSpare(List<Integer> rolls, int rollIndex) {
        return rolls.get(rollIndex) + rolls.get(rollIndex + 1) == MAX_PINS;
    }
}