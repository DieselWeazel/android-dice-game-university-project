package com.thirty.service;

public class DiceThrowHelper {

    private DiceResult diceResult;

    public DiceResult getDiceCastResult() {
        this.diceResult = new DiceResult();

        return diceResult;
    }

    public DiceResult getDiceResult() {
        return diceResult;
    }
}
