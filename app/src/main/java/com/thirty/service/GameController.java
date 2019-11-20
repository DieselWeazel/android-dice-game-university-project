package com.thirty.service;

public class GameController {

    private DiceResult diceResult;

    public DiceResult getDiceCastResult() {
        this.diceResult = new DiceResult();

        return diceResult;
    }

    /**
     * Returns the result of all 6 dices cast.
     *
     * @return 6 dices, or 6 ints of random range between 1 and 6.
     */
    public DiceResult getDiceResult() {
        return diceResult;
    }

    /**
     * Sets the DiceResult, in case of rotation and data loss, the controller needs a heads up about what it lost.
     *
     * @param diceResult being the 6 dices, or 6 ints stored inside instance state
     */
    public void setDiceResult(DiceResult diceResult) {
        this.diceResult = diceResult;
    }
}
