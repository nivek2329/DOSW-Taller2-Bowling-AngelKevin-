package edu.eci.dosw.bowling;

import java.util.ArrayList;
import java.util.List;


public class Frame {

    private final List<Integer> rolls;
    private FrameType type;

    public Frame() {
        this.rolls = new ArrayList<>();
        this.type = FrameType.NORMAL;
    }

    public void addRoll(int pins) {
        rolls.add(pins);
    }

    public List<Integer> getRolls() {
        return List.copyOf(rolls);
    }

    public FrameType getType() {
        return type;
    }

    public void setType(FrameType type) {
        this.type = type;
    }


    public boolean isFull() {
        if (type == FrameType.TENTH) {
            if (rolls.size() < 2) {
                return false;
            }
            if (rolls.size() == 2) {
                boolean strike = rolls.get(0) == 10;
                boolean spare = !strike && (rolls.get(0) + rolls.get(1) == 10);
                return !(strike || spare);
            }
            return true;
        }
        if (type == FrameType.STRIKE) {
            return true;
        }
        return rolls.size() >= 2;
    }
}