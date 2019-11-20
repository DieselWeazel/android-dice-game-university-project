package com.thirty.service;

import com.thirty.model.Dice;
import com.thirty.model.Points;

import java.util.List;

public class PointCollector {

    public void gatherPoints(boolean[] dicesChosenForPoints) {
        for (int i = 0; i < dicesChosenForPoints.length; i++) {

        }
    }

    public int collectAndReturnPoints(int[] diceScore) {
        int points = 0;
        boolean[] diceScoreCollected = new boolean[]{false, false, false, false, false, false};
        for (int i = 0; i < diceScore.length; i++) {
            for (int j = 0; j < diceScore.length; j++) {
                if ((diceScore[i] == diceScore[j]) && (diceScoreCollected[i] == false && diceScoreCollected[j] == false)) {
                    diceScoreCollected[i] = true;
                    diceScoreCollected[j] = true;

                    points += diceScore[i] + diceScore[j];
                }
            }
        }
        return points;
    }
}
