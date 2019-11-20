package com.thirty.model;

/**
 * Model Dice Class, set to mimmick a real Dice.
 */
public class Dice {

    private int diceNumber;
    private boolean diceKeeper;
    private boolean diceRoll;

    /**
     * Constructor, sets up a Dice number by calling the setDiceInt() method
     */
    public Dice() {
        this.diceNumber = setDiceInt();
    }

    /**
     * Sets a Dice with chosen Dice Number
     *
     * @param diceNumber being the Dice Number stored upon savedInstanceState
     */
    public Dice(int diceNumber) {
        this.diceNumber = diceNumber;
    }

    /**
     * Sets Dice Int
     *
     * @return Random Number 1-6 like a Dice.
     */
    public int setDiceInt() {
        return (int) ((6 * Math.random()) + 0);
    }

    /**
     * Rolls Dice, calls the setDiceInt() method. More or less just like the constructor, except we keep our booleans.
     */
    public void rollDice() {
        diceNumber = setDiceInt();
    }

    /**
     * Sets DiceNumber, in case of Saved Instance State.
     *
     * @param diceNumber
     */
    public void setDiceNumber(int diceNumber) {
        this.diceNumber = diceNumber;
    }

    /**
     * Gets Dice Number (int)
     */
    public int getDiceNumber() {
        return diceNumber;
    }

    /**
     * Checks if Dice is decided to be a keeper.
     *
     * @return
     */
    public boolean isDiceKeeper() {
        return diceKeeper;
    }

    /**
     * Sets the Dice to be a keeper in case of reroll.
     *
     * @param diceKeeper
     */
    public void setDiceKeeper(boolean diceKeeper) {
        this.diceKeeper = diceKeeper;
    }

    /**
     * Checks if Dice is Rolling.
     *
     * @return
     */
    public boolean isDiceRoll() {
        return diceRoll;
    }

    /**
     * Sets Dice to rolling status.
     *
     * @param diceRoll
     */
    public void setDiceRoll(boolean diceRoll) {
        this.diceRoll = diceRoll;
    }
}
