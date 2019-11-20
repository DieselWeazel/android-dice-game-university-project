package com.thirty.service;

import android.util.Log;

import com.thirty.model.Dice;

import java.util.ArrayList;
import java.util.List;

/**
 * Dice Result class, I seperated some of the logic to here because I thought it would become too big otherwise.
 */
public class DiceResult {

    protected static final String TAG = "DiceResult";
    protected List<Dice> diceList;

    /**
     * Constructor, sets the DiceList.
     */
    public DiceResult() {
        diceList = getSixRolledDices();
    }

    /**
     * Constructor with setting the diceList, in case of SavedInstanceState
     *
     * @param diceList
     */
    public DiceResult(List<Dice> diceList) {
        this.diceList = diceList;
    }

    /**
     * Gets Six rolled dices. Because of tiredness and timeloss I have to leave them like this. Normally I would loop through it.
     *
     * @return
     */
    private List<Dice> getSixRolledDices() {
        List<Dice> diceList = new ArrayList<>();
        Dice dice1 = new Dice();
        Dice dice2 = new Dice();
        Dice dice3 = new Dice();
        Dice dice4 = new Dice();
        Dice dice5 = new Dice();
        Dice dice6 = new Dice();
        dice1.setDiceKeeper(true);
        dice2.setDiceKeeper(true);
        dice3.setDiceKeeper(true);
        dice4.setDiceKeeper(true);
        dice5.setDiceKeeper(true);
        dice6.setDiceKeeper(true);
        dice1.setDiceRoll(true);
        dice2.setDiceRoll(true);
        dice3.setDiceRoll(true);
        dice4.setDiceRoll(true);
        dice5.setDiceRoll(true);
        dice6.setDiceRoll(true);
        diceList.add(dice1);
        diceList.add(dice2);
        diceList.add(dice3);
        diceList.add(dice4);
        diceList.add(dice5);
        diceList.add(dice6);

        String diceInformationString = "";
        for (int i = 0; i < diceList.size(); i++) {
            diceInformationString += diceList.get(i).getDiceNumber() + ".. ";
        }
        Log.i(TAG, diceInformationString);
        return diceList;
    }

    /**
     * Gets the List of Dices.
     *
     * @return
     */
    public List<Dice> getDiceList() {
        return diceList;
    }


}
