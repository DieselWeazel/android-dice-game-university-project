package com.thirty.service;

import android.util.Log;

import com.thirty.model.Dice;

import java.util.List;

/**
 * Controller class meant to handle the Dice Game Logic.
 */
public class DiceGameController {

    protected static final String TAG = "DiceGameController";
    protected static final String ROUNDTAG = "CollectedRoundPoint";
    private static String diceGameControllerMessage = "";

    // -- Game Variables -- //
    private DiceResult diceResult;

    // -- Game Control Variables -- //
    private int rollDiceCounter;
    private int roundCounter;
    private int roundScoreCounter;
    private int collectedRoundPoint;
    private int chosenGamePoint;

    public static boolean rollDicePossibility = true;
    private static boolean allDicePointsCollected = false;
    private boolean lowDiceCalculation = false;
    private boolean changeDiceCalculationString = false;

    private int[] totalGamePoints = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private boolean[] gamePointTakenList = new boolean[]{false, false, false, false, false, false, false, false, false, false};
    private String[] gameRoundResultList = new String[]{"", "", "", "", "", "", "", "", "", ""};

    /**
     * Constructor for roundCounter and RollDice initiation.
     */
    public DiceGameController() {
        rollDiceCounter = 0;
        roundCounter = 0;
    }

    /**
     * Sets the first rollout of Dices. All Dices are now rolles.
     */
    public void getFirstDiceCastResult() {
        this.diceResult = new DiceResult();
        rollDiceCounter++;
        Log.i(TAG, "Roll: " + rollDiceCounter);
    }

    /**
     * Casts the Second loadout of Dices. This time we see which dices we want to keep and call their rollDice() function.
     */
    public void getNextDiceCastResult() {
        diceGameControllerMessage = "";
        String diceBoolean = "";
        rollDiceCounter++;
        if (rollDiceCounter <= 3) {
            for (Dice d : diceResult.getDiceList()) {
                diceBoolean += "d" + diceResult.getDiceList().indexOf(d) + " is " + d.isDiceKeeper() + ", ";
                if (!d.isDiceKeeper()) {
                    diceGameControllerMessage += "<<Dice: " + diceResult.getDiceList().indexOf(d) + " rerolled to : ";
                    d.rollDice();

                    diceGameControllerMessage += d.getDiceNumber() + ">>";
                    d.setDiceKeeper(true);
                }
            }

            Log.i(TAG, diceGameControllerMessage);
        } else {
            rollDicePossibility = false;
            diceGameControllerMessage += "RollDicePossibility is now false";
            Log.i(TAG, diceGameControllerMessage);
        }
    }

    /**
     * Turns all Dices into an int array, this way when it's called several times for multiple calculations the dices are never 0 when returning from the recursive function.
     *
     * @param chosenPoints being the desired point to score.
     */
    public void calculateDices(int chosenPoints) {
        int[] intDiceList = new int[6];
        for (Dice d : diceResult.getDiceList()) {
            intDiceList[diceResult.getDiceList().indexOf(d)] = d.getDiceNumber() + 1;
        }
        for (int i = 1; i < 6; i++) {
            calculateDiceCombination(intDiceList, i, chosenPoints);
        }
        if (!lowDiceCalculation) {
            finishRoundAndResetController();
        }
    }

    /**
     * If User selects the "Low" option, this calculates what gives the User the most points by going through the calculateDiceCombination method
     * 3 times. Then comparing the 3 resulted output scores, setting the highest one as the CollectedRoundPoint, and then calling
     * the finishRoundAndResetController() method just like we would if we were just looking through one score.
     */
    public void giveUserMostPointsOfALowScore() {
        lowDiceCalculation = true;
        int[] lowDiceValues = new int[]{1, 2, 3};
        for (int i = 0; i < lowDiceValues.length; i++) {
            collectedRoundPoint = 0;
            calculateLowDice(lowDiceValues[i]);
            lowDiceValues[i] = collectedRoundPoint;
        }

        for (int i = 0; i < lowDiceValues.length; i++) {
            if (lowDiceValues[0] < lowDiceValues[i]) {
                lowDiceValues[0] = lowDiceValues[i];
            }
        }
        lowDiceCalculation = false;
        collectedRoundPoint = lowDiceValues[0];
        changeDiceCalculationString = true;
        finishRoundAndResetController();
    }

    /**
     * Calculates the score of a sum of dices between 1 to 3.
     *
     * @param lowScore
     */
    private void calculateLowDice(int lowScore) {
        calculateDices(lowScore);
    }

    /**
     * Calculates Dices
     *
     * @param dice         being the list of Dices to calculate. (Turnt to 0 inside the recursive function if they are counted.
     * @param amountOfDice being the amount of dice to try in the loop
     * @param chosenPoints being the amount of points desired.
     */
    private void calculateDiceCombination(int[] dice, int amountOfDice, int chosenPoints) {
        if (!lowDiceCalculation) {
            chosenGamePoint = chosenPoints;
        }
        // If all dices add up to the chosen point, break entire function.
        if (amountOfDice == 6) {
            if (checkIfAllDicesMakeTheScore(dice, chosenPoints)) return;
        }

        // Calculates if 1 make the score
        if (amountOfDice == 1) {
            checkIfOneDiceIsEnough(dice, amountOfDice, chosenPoints);
        }

        // Calculates if 2 Dices make the score
        if (amountOfDice == 2) {
            calculate2Dices(dice, amountOfDice, chosenPoints);
        }

        // Calculates if 3 Dices make the score
        if (amountOfDice == 3) {
            calculate3Dices(dice, amountOfDice, chosenPoints);
        }

        // Calculates if 4 Dices make the score
        if (amountOfDice == 4) {
            calculate4Dices(dice, amountOfDice, chosenPoints);
        }

        // Calculates if 5 dices make the score
        if (amountOfDice == 5) {
            calculate5Dices(dice, amountOfDice, chosenPoints);
        }
    }

    /**
     * Checks if All Dices together make the score, returns true if so.
     *
     * @param dice         being the DiceList.
     * @param chosenPoints being the chosen points to calculate towards
     * @return
     */
    private boolean checkIfAllDicesMakeTheScore(int[] dice, int chosenPoints) {
        if (dice[0] + dice[1] + dice[2] + dice[3] + dice[4] + dice[5] == chosenPoints) {
            roundScoreCounter += 1;
            allDicePointsCollected = true;
            return true;
        }
        return false;
    }

    /**
     * Checks if any of the 6 dices sum of to the score
     *
     * @param dice         being the dicelist.
     * @param amountOfDice of dice combined.
     * @param chosenPoints being the points to count towards
     */
    private void checkIfOneDiceIsEnough(int[] dice, int amountOfDice, int chosenPoints) {
        for (int i = 0; i < dice.length; i++) {
            if (dice[i] == chosenPoints) {
                // SCORE

                collectedRoundPoint += chosenPoints;
                dice[i] = 0;
                roundScoreCounter += 1;
                calculateDiceCombination(dice, amountOfDice, chosenPoints);
            }
        }
    }

    /**
     * Calculates dices of 5 to see if 2 can sum up to the score.
     *
     * @param dice         being the dicelist.
     * @param amountOfDice of dice combined.
     * @param chosenPoints being the points to count towards
     */
    private void calculate2Dices(int[] dice, int amountOfDice, int chosenPoints) {
        for (int i = 0; i < dice.length; i++) {
            for (int j = i + 1; j < dice.length; j++) {
                if (dice[i] + dice[j] == chosenPoints) {
                    dice[i] = 0;
                    dice[j] = 0;

                    collectedRoundPoint += chosenPoints;
                    roundScoreCounter += 1;
                    calculateDiceCombination(dice, amountOfDice, chosenPoints);
                }
            }
        }
    }

    /**
     * Calculates dices of 3 to see if 5 can sum up to the score.
     *
     * @param dice         being the dicelist.
     * @param amountOfDice of dice combined.
     * @param chosenPoints being the points to count towards
     */
    private void calculate3Dices(int[] dice, int amountOfDice, int chosenPoints) {
        for (int i = 0; i < dice.length - 2; i++) {
            for (int j = i + 1; j < dice.length - 1; j++) {
                for (int k = j + 1; k < dice.length; k++) {
                    if (dice[i] + dice[j] + dice[k] == chosenPoints) {
                        dice[i] = 0;
                        dice[j] = 0;
                        dice[k] = 0;

                        collectedRoundPoint += chosenPoints;
                        roundScoreCounter += 1;
                        calculateDiceCombination(dice, amountOfDice, chosenPoints);
                    }
                }
            }
        }
    }

    /**
     * Calculates dices of 4 to see if 5 can sum up to the score.
     *
     * @param dice         being the dicelist.
     * @param amountOfDice of dice combined.
     * @param chosenPoints being the points to count towards
     */
    private void calculate4Dices(int[] dice, int amountOfDice, int chosenPoints) {
        for (int i = 0; i < dice.length - 3; i++) {
            for (int j = i + 1; j < dice.length - 2; j++) {
                for (int k = j + 1; k < dice.length - 1; k++) {
                    for (int l = k + 1; l < dice.length; l++) {
                        if (dice[i] + dice[j] + dice[k] + dice[l] == chosenPoints) {
                            dice[i] = 0;
                            dice[j] = 0;
                            dice[k] = 0;
                            dice[l] = 0;

                            collectedRoundPoint += chosenPoints;
                            roundScoreCounter += 1;
                            calculateDiceCombination(dice, amountOfDice, chosenPoints);
                        }
                    }
                }
            }
        }
    }

    /**
     * Calculates dices of 5 to see if 5 can sum up to the score.
     *
     * @param dice         being the dicelist.
     * @param amountOfDice of dice combined.
     * @param chosenPoints being the points to count towards
     */
    private void calculate5Dices(int[] dice, int amountOfDice, int chosenPoints) {
        for (int i = 0; i < dice.length - 4; i++) {
            for (int j = i + 1; j < dice.length - 3; j++) {
                for (int k = j + 1; k < dice.length - 2; k++) {
                    for (int l = k + 1; l < dice.length - 1; l++) {
                        for (int p = l + 1; p < dice.length; p++) {
                            if (dice[i] + dice[j] + dice[k] + dice[l] + dice[p] == chosenPoints) {
                                dice[i] = 0;
                                dice[j] = 0;
                                dice[k] = 0;
                                dice[l] = 0;
                                dice[p] = 0;

                                collectedRoundPoint += chosenPoints;
                                roundScoreCounter += 1;
                                calculateDiceCombination(dice, amountOfDice, chosenPoints);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Resets and finished the current round, saves the total round score into the index of which the round is on.
     */
    public void finishRoundAndResetController() {
        totalGamePoints[roundCounter] = collectedRoundPoint;
        if (!changeDiceCalculationString) {
            gameRoundResultList[roundCounter] += "Choosing " + chosenGamePoint + ", gave you a total of: " + collectedRoundPoint + ".\n";
        } else {
            gameRoundResultList[roundCounter] += "Choosing LOW, gave you a total of: " + collectedRoundPoint + ".\n";
        }
        changeDiceCalculationString = false;
        rollDiceCounter = 0;
        if (!lowDiceCalculation) {
            roundCounter++;
        }
        Log.i(ROUNDTAG, "Score: " + collectedRoundPoint + "Round: " + roundCounter);
        resetTotalRoundScore();
    }

    /**
     * A total calculation of all points * rounds earned, IE the total game score.
     *
     * @return
     */
    public int getTotalCalculationOfGamePoints() {
        int sum = 0;
        for (int i = 0; i < totalGamePoints.length; i++) {
            sum += totalGamePoints[i];
        }
        return sum;
    }

    /**
     * Gets the total array of game points, for storing in-case of data loss.
     *
     * @return array of all collected game points, no more than 10.
     */
    public int[] getTotalGamePoints() {
        return totalGamePoints;
    }

    /**
     * Sets the total array of game points, if they were lost in a data loss.
     *
     * @param totalGamePoints being the array of points collected and stored within savedinstancestate, and now returned to the DiceGameController.
     */
    public void setTotalGamePoints(int[] totalGamePoints) {
        this.totalGamePoints = totalGamePoints;
    }

    /**
     * Gets the total list of taken points, if true that means the point is already collected. Also gets them for savedinstancestate
     *
     * @return a list of all taken, and non-taken game-points by index number. (0-9, reflecting the 10 choices of the list)
     */
    public boolean[] getGamePointTakenList() {
        return gamePointTakenList;
    }

    /**
     * Sets the points taken, if a point is taken, or if restoring to a saved instance state.
     *
     * @param gamePointTakenList
     */
    public void setGamePointTakenList(boolean[] gamePointTakenList) {
        this.gamePointTakenList = gamePointTakenList;
    }

    /**
     * Gets the round Counter in-case of data loss.
     *
     * @return the round counter.
     */
    public int getRoundCounter() {
        return roundCounter;
    }

    /**
     * Sets the round counter, in case of saved instance state.
     *
     * @param roundCounter being the counter of rounds that have passed within the savedinstancestate.
     */
    public void setRoundCounter(int roundCounter) {
        this.roundCounter = roundCounter;
    }

    /**
     * Gets the total score of the round.
     *
     * @return the total score, IE the highest calculated sum of the given points value to find as many combinations out of.
     */
    public int getTotalRoundScore() {
        return collectedRoundPoint;
    }

    /**
     * If round score calculation wasn't correct, reset the counter and the collected points, so it doesn't add up with the final sum.
     */
    public void resetTotalRoundScore() {
        roundScoreCounter = 0;
        collectedRoundPoint = 0;
    }

    /**
     * Sets all Dices to neutral mode, IE they lying down. The false indicator is to make sure they aren't rerolled during the next rounds reroll since the user
     * has to pick which dices to reroll or not.
     */
    public void groundAllDices() {
        for (Dice d : diceResult.getDiceList()) {
            d.setDiceRoll(false);
        }
    }

    /**
     * Boolean checking if Dice Buttons should be available or not. Mainly used for GameActivity so the UI doesn't allow for Dice renewal.
     *
     * @return
     */
    public boolean isDiceButtonsAvailable() {
        return (rollDiceCounter <= 2);
    }

    /**
     * Returns true if game has seen one Roll of Dices, so points actually can be collected.
     *
     * @return
     */
    public boolean readyToCollectPoints() {
        Log.i(TAG, "readyToCollect Points: " + (rollDiceCounter >= 1));
        return (rollDiceCounter >= 1);
    }

    /**
     * Gets the Roll Dice Counter, to see the times Dices have been rolled.
     *
     * @return
     */
    public int getRollDiceCounter() {
        return rollDiceCounter;
    }

    /**
     * Sets the Roll Dice counter in case of a saved instance state.
     *
     * @param rollDiceCounter
     */
    public void setRollDiceCounter(int rollDiceCounter) {
        this.rollDiceCounter = rollDiceCounter;
    }

    /**
     * Sets all Roll Dice Possibilities to true.
     *
     * @param diceButtonsAvailable
     */
    public void setDiceButtonsAvailable(boolean diceButtonsAvailable) {
        DiceGameController.rollDicePossibility = diceButtonsAvailable;
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

    /**
     * Gets the String list of round results.
     *
     * @return
     */
    public String[] getGameRoundResultList() {
        return gameRoundResultList;
    }

    /**
     * Sets the String list of round results.
     *
     * @param gameRoundResultList
     */
    public void setGameRoundResultList(String[] gameRoundResultList) {
        this.gameRoundResultList = gameRoundResultList;
    }
}
