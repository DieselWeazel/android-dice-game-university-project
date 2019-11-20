package com.thirty.ui;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.thirty.model.Dice;
import com.thirty.service.DiceResult;
import com.thirty.service.DiceGameController;

import java.util.ArrayList;
import java.util.List;

/**
 * UI for the Game Activity, handles all graphical input and output of the game. Implements AdapterView in order to connect with a Spinner easily.
 */
public class GameActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // -- Tags, Keys -- //
    protected static final String TAG = "GameActivity";
    protected static final String DICE_INT_ARRAY_KEY = "com.thirty.ui.DICE_INT_ARRAY";
    protected static final String IS_POINTS_BUTTON_ENABLED_KEY = "com.thirty.ui.IS_POINTS_BUTTON_ENABLED_KEY";
    protected static final String IS_THROW_DICE_BUTTON_ENABLED_KEY = "com.thirty.ui.IS_THROW_DICE_BUTTON_ENABLED_KEY";
    protected static final String DICE_ROLL_COUNTER_KEY = "com.thirty.ui.DICE_ROLL_COUNTER_KEY";
    protected static final String GAME_ROUND_COUNTER_KEY = "com.thirty.ui.GAME_ROUND_COUNTER_KEY";
    protected static final String TOTAL_GAME_POINTS_KEY = "com.thirty.ui.TOTAL_GAME_POINTS_KEY";
    protected static final String ARRAY_GAME_CHOSEN_POINTS_BOOLEANS_KEY = "ARRAY_GAME_CHOSEN_POINTS_BOOLEANS_KEY";
    protected static final String ARRAY_GAME_SCORE_KEY = "com.thirty.ui.GAME_SCORE_KEY";
    protected static final String DICE_KEEPER_BOOLEAN_ARRAY = "com.thirty.ui.DICE_KEEPER_BOOLEAN_ARRAY";
    protected static final String ROUND_RESULT_STRING_ARRAY_KEY = "com.thirty.ui.ROUND_RESULT_STRING_ARRAY_KEY";
    private String diceInformationString = "";

    private Button throwDiceButton;
    private Button gatherPointsButton;
    private Spinner pointsSpinner;
    private TextView pointsTextView;
    private ImageButton diceButton1;
    private ImageButton diceButton2;
    private ImageButton diceButton3;
    private ImageButton diceButton4;
    private ImageButton diceButton5;
    private ImageButton diceButton6;

    // -- Spinner Assisting Variables -- //
    private ArrayAdapter<CharSequence> adapter;
    private String spinnerString = "";
    private int spinnerPosition = 0;

    // -- ArrayLists -- //
    // Control, Variable or ID Lists
    private int[] diceButtonIdList = new int[]{R.id.button_dice_1, R.id.button_dice_2, R.id.button_dice_3, R.id.button_dice_4, R.id.button_dice_5, R.id.button_dice_6};
    private ImageButton[] diceButtonImageButtonList = new ImageButton[]{diceButton1, diceButton2, diceButton3, diceButton4, diceButton5, diceButton6};
    private boolean[] imageButtonKeeper = new boolean[6];

    // Images
    private int[] diceImageListWhite = new int[]{R.drawable.white1, R.drawable.white2, R.drawable.white3, R.drawable.white4, R.drawable.white5, R.drawable.white6};
    private int[] diceImageListRed = new int[]{R.drawable.red1, R.drawable.red2, R.drawable.red3, R.drawable.red4, R.drawable.red5, R.drawable.red6};
    private int[][] diceImageListRotation = new int[][]{{R.drawable.grey1, R.drawable.grey2, R.drawable.grey3, R.drawable.grey4, R.drawable.grey5, R.drawable.grey6},
            {R.drawable.white1, R.drawable.white2, R.drawable.white3, R.drawable.white4, R.drawable.white5, R.drawable.white6}};

    // StorageLists, upon rotation etc.
    private int[] diceIntNumbers = new int[6];
    private boolean[] diceIsKeeper = new boolean[6];

    private int[] gamePointResultList = new int[10];
    private String gamePointTotalString = "";
    private static boolean rolledDiceAndSavedInstanceState;

    // -- Game Related Variables -- //
    private DiceGameController diceGameController = new DiceGameController();

    /**
     * Creates the UI
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (savedInstanceState != null) {
            diceInformationString = "onCreate(savedInstanceState!=null) ";

            String booleanArrayInfoString = "boolean keepers: ";
            List<Dice> diceList = new ArrayList<>();
            diceGameController.setRollDiceCounter(savedInstanceState.getInt(DICE_ROLL_COUNTER_KEY));
            diceGameController.setRoundCounter(savedInstanceState.getInt(GAME_ROUND_COUNTER_KEY));
            Log.i(TAG, "Vaue of dice counter: " + diceGameController.getRollDiceCounter());
            if (diceGameController.getRollDiceCounter() >= 1) {
                imageButtonKeeper = savedInstanceState.getBooleanArray(DICE_KEEPER_BOOLEAN_ARRAY);
                diceIntNumbers = savedInstanceState.getIntArray(DICE_INT_ARRAY_KEY);
                Log.i(TAG, diceInformationString + " picks up boolean Array of size: " + imageButtonKeeper.length);


                for (int i = 0; i < diceIntNumbers.length; i++) {
                    Dice restoreDice = new Dice(diceIntNumbers[i]);
                    diceList.add(restoreDice);
                    diceInformationString += restoreDice.getDiceNumber() + ".. ";
                    booleanArrayInfoString += "Dice: " + (i + 1) + " in boolean: " + imageButtonKeeper[i] + ".. ";
                }
                DiceResult diceResult = new DiceResult(diceList);
                Log.i(TAG, diceInformationString);
                Log.i(TAG, booleanArrayInfoString);
                diceGameController.setDiceResult(diceResult);
            }

            if (diceGameController.getRoundCounter() >= 1) {
                int[] points = savedInstanceState.getIntArray(ARRAY_GAME_SCORE_KEY);
                int sum = 0;
                for (int i = 0; i < points.length; i++) {
                    sum += points[i];
                }

                boolean[] takenAnswers = savedInstanceState.getBooleanArray(ARRAY_GAME_CHOSEN_POINTS_BOOLEANS_KEY);
                String answersTakenString = "";
                for (int i = 0; i < takenAnswers.length; i++) {
                    answersTakenString += i + ": " + takenAnswers[i] + ", ";
                }
                Log.i(TAG, "Rounds: " + diceGameController.getRoundCounter() + ", gamePoints so far: " + sum + ", the following answers have been taken: " + answersTakenString);
                diceGameController.setTotalGamePoints(savedInstanceState.getIntArray(ARRAY_GAME_SCORE_KEY));
                diceGameController.setGamePointTakenList(savedInstanceState.getBooleanArray(ARRAY_GAME_CHOSEN_POINTS_BOOLEANS_KEY));
                diceGameController.setGameRoundResultList(savedInstanceState.getStringArray(ROUND_RESULT_STRING_ARRAY_KEY));
                gamePointTotalString = getResources().getString(R.string.current_point_message) + diceGameController.getTotalCalculationOfGamePoints();

            }

            init();


            throwDiceButton.setEnabled(savedInstanceState.getBoolean(IS_THROW_DICE_BUTTON_ENABLED_KEY));
            gatherPointsButton.setEnabled(savedInstanceState.getBoolean(IS_POINTS_BUTTON_ENABLED_KEY));

            if (diceGameController.getRollDiceCounter() >= 1) {
                rolledDiceAndSavedInstanceState = true;
                setRolledDiceImages();
            } else {
                rolledDiceAndSavedInstanceState = false;
            }
        } else {
            rolledDiceAndSavedInstanceState = false;
            init();
        }
    }

    /**
     * Saves all relevant data upon rotation or shifting between activies.
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        diceInformationString = "onSaveInstanceState ";

        String diceBooleanDebugString = "diceBooleanDebugString ";
        // Saves all current dice
        if (diceGameController.getRollDiceCounter() >= 1) {
            for (int i = 0; i < diceGameController.getDiceResult().getDiceList().size(); i++) {
                diceIntNumbers[i] = diceGameController.getDiceResult().getDiceList().get(i).getDiceNumber();
                diceInformationString += diceIntNumbers[i] + ".. ";
            }
            outState.putIntArray(DICE_INT_ARRAY_KEY, diceIntNumbers);
            outState.putBooleanArray(DICE_KEEPER_BOOLEAN_ARRAY, imageButtonKeeper);
            for (int i = 0; i < imageButtonKeeper.length; i++) {
                diceBooleanDebugString += i + " is " + imageButtonKeeper;
            }
        }
        Log.i(TAG, diceBooleanDebugString);
        Log.i(TAG, diceInformationString);

        outState.putBoolean(IS_POINTS_BUTTON_ENABLED_KEY, diceGameController.readyToCollectPoints());
        outState.putBoolean(IS_THROW_DICE_BUTTON_ENABLED_KEY, diceGameController.isDiceButtonsAvailable());
        outState.putInt(GAME_ROUND_COUNTER_KEY, diceGameController.getRoundCounter());
        if (diceGameController.getRoundCounter() >= 1) {
            outState.putIntArray(ARRAY_GAME_SCORE_KEY, diceGameController.getTotalGamePoints());
            outState.putBooleanArray(ARRAY_GAME_CHOSEN_POINTS_BOOLEANS_KEY, diceGameController.getGamePointTakenList());
            outState.putStringArray(ROUND_RESULT_STRING_ARRAY_KEY, diceGameController.getGameRoundResultList());
        }
        outState.putInt(DICE_ROLL_COUNTER_KEY, diceGameController.getRollDiceCounter());
    }


    /**
     * Initiates
     */
    private void init() {
        throwDiceButton = findViewById(R.id.button_throwdice);
        gatherPointsButton = findViewById(R.id.button_make_selection);
        pointsSpinner = findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.desired_Points, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pointsSpinner.setAdapter(adapter);
        pointsSpinner.setOnItemSelectedListener(this);
        pointsTextView = findViewById(R.id.points_text_view);

        if (diceGameController.getRoundCounter() >= 1) {
            pointsTextView.setText(gamePointTotalString);
        }
        setRoundStart();
    }

    /**
     * ThrowDice method, first creates a spinning animation out of all the colors, then sets them. The dice values are however set during the spin,
     * in case of a rotation or data loss.
     */
    private void throwDice() {
        if (diceGameController.getRollDiceCounter() < 1) {
            diceGameController.getFirstDiceCastResult();
        }

        new CountDownTimer(1500, 150) {

            @Override
            public void onTick(long l) {
                throwDiceButton.setEnabled(false);
                gatherPointsButton.setEnabled(false);

                for (int i = 0; i < diceButtonImageButtonList.length; i++) {

                    if (diceGameController.getDiceResult().getDiceList().get(i).isDiceRoll()) {
                        diceButtonImageButtonList[i].setImageResource(diceImageListRotation[(int) (Math.random() + 0.5)][(int) (5 * Math.random() + 0)]);
                    }
                }
            }

            @Override
            public void onFinish() {
                setRolledDiceImages();
            }
        }.start();
    }

    /**
     * Sets the dices to their designated values. Gives them all a toggle-like function, if pressed they are considered selected for a new throw, if not selected they are desired as keepers.
     */
    private void setRolledDiceImages() {
        diceInformationString = "setRolledDiceImages ";
        gatherPointsButton.setEnabled(true);
        diceGameController.groundAllDices();
        for (int i = 0; i < diceButtonImageButtonList.length; i++) {
            diceButtonImageButtonList[i].setImageResource(diceImageListWhite[diceGameController.getDiceResult().getDiceList().get(i).getDiceNumber()]);
            diceInformationString += diceGameController.getDiceResult().getDiceList().get(i).getDiceNumber() + ".. ";
            if (!rolledDiceAndSavedInstanceState) {
                imageButtonKeeper[i] = false;
            }
        }
        Log.i(TAG, diceInformationString);

        for (int i = 0; i < diceButtonImageButtonList.length; i++) {
            diceButtonImageButtonList[i].setEnabled(true);
            int finalI = i;
            if (!rolledDiceAndSavedInstanceState) {
                imageButtonKeeper[i] = false;

            } else {
                if (imageButtonKeeper[finalI]) {
                    diceButtonImageButtonList[finalI].setImageResource(diceImageListRed[diceGameController.getDiceResult().getDiceList().get(finalI).getDiceNumber()]);
                    diceGameController.getDiceResult().getDiceList().get(finalI).setDiceKeeper(false);
                    diceGameController.getDiceResult().getDiceList().get(finalI).setDiceRoll(true);
                } else {
                    diceButtonImageButtonList[finalI].setImageResource(diceImageListWhite[diceGameController.getDiceResult().getDiceList().get(finalI).getDiceNumber()]);
                    diceGameController.getDiceResult().getDiceList().get(finalI).setDiceKeeper(true);
                    diceGameController.getDiceResult().getDiceList().get(finalI).setDiceRoll(false);
                }

            }

            diceButtonImageButtonList[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (!imageButtonKeeper[finalI]) {
                        diceButtonImageButtonList[finalI].setImageResource(diceImageListRed[diceGameController.getDiceResult().getDiceList().get(finalI).getDiceNumber()]);
                        diceGameController.getDiceResult().getDiceList().get(finalI).setDiceKeeper(false);
                        diceGameController.getDiceResult().getDiceList().get(finalI).setDiceRoll(true);
                        imageButtonKeeper[finalI] = true;
                    } else {
                        diceButtonImageButtonList[finalI].setImageResource(diceImageListWhite[diceGameController.getDiceResult().getDiceList().get(finalI).getDiceNumber()]);
                        diceGameController.getDiceResult().getDiceList().get(finalI).setDiceKeeper(true);
                        diceGameController.getDiceResult().getDiceList().get(finalI).setDiceRoll(false);
                        imageButtonKeeper[finalI] = false;
                    }
                }
            });
        }
        if (diceGameController.isDiceButtonsAvailable()) {
            throwDiceButton.setEnabled(true);
            throwDiceButton.setOnClickListener(e -> {
                diceGameController.getNextDiceCastResult();
                setAllDiceImageButtonKeepersToFalse();
                throwDice();
            });
        }
        gatherPointsButton.setOnClickListener(gatherPoints -> {
            if (!diceGameController.getGamePointTakenList()[spinnerPosition]) {
                if (spinnerString.equals("Low Point Score, 1 to 3")) {
                    diceGameController.giveUserMostPointsOfALowScore();
                } else {
                    diceGameController.calculateDices(Integer.valueOf(spinnerString));
                }
                diceGameController.getGamePointTakenList()[spinnerPosition] = true;
                setNextRoundUp();

            } else {
                String s = spinnerString + " is already collected..";
                Log.i(TAG, "User tried to collect points that aren't available");

                // Because calculating the score sums up a point value, if the user tries to calculate a faulty score, I reset the values by calling this method.
                diceGameController.resetTotalRoundScore();
                Toast.makeText(this, s, Toast.LENGTH_LONG).show();
                return;
            }
        });
    }

    /**
     * Upon throwing dices anew, in order to reset their saved status, they are all falsified in this method.
     */
    private void setAllDiceImageButtonKeepersToFalse() {
        for (int i = 0; i < imageButtonKeeper.length; i++) {
            imageButtonKeeper[i] = false;
        }
    }

    /**
     * Sets up the next round upon sucessfully gathering points.
     */
    private void setNextRoundUp() {
        if (diceGameController.getRoundCounter() < 10) {
            diceGameController.getGamePointTakenList()[spinnerPosition] = true;

            rolledDiceAndSavedInstanceState = false;
            // Calls the DiceGameController to reset it's counting values to prepare for a new round.
            gamePointTotalString = "Total score: " + diceGameController.getTotalCalculationOfGamePoints();
            pointsTextView.setText(gamePointTotalString);
            Log.i(TAG, "TOTAL POINTS: " + diceGameController.getTotalCalculationOfGamePoints());
            setRoundStart();
        } else {
            // Game is over!
            goToGameOverScreen();

        }
    }

    /**
     * Launches a Game Over Screen, collects all data and sends to the UI.
     */
    private void goToGameOverScreen() {

        Intent intent = new Intent(this, GameOverActivity.class);

        intent.putExtra(ARRAY_GAME_SCORE_KEY, diceGameController.getTotalGamePoints());
        intent.putExtra(TOTAL_GAME_POINTS_KEY, gamePointTotalString);
        intent.putExtra(ROUND_RESULT_STRING_ARRAY_KEY, diceGameController.getGameRoundResultList());
        startActivity(intent);
        finish();
    }

    /**
     * Sets the UI to a new round.
     * Sets the Dice Images to original grey state, when the game has just begun or when a new round is begun.
     * sets the gather points button to disabled since we can't gather points unless we have spin our dice.
     * Sets the throwDiceButton to its onClickListenerEvent, if clicked, throwDice() method will be called initiating the spin of Dices.
     */
    private void setRoundStart() {
        for (int i = 0; i < diceButtonImageButtonList.length; i++) {
            diceButtonImageButtonList[i] = findViewById(diceButtonIdList[i]);
            diceButtonImageButtonList[i].setImageResource(diceImageListRotation[0][i]);
        }
        gatherPointsButton.setEnabled(false);
        throwDiceButton.setEnabled(true);
        throwDiceButton.setOnClickListener(e -> throwDice());
    }


    /**
     * collects the data from the Spinner, of which is the list of points available to choose for the game.
     *
     * @param adapterView being the spinner selection model
     * @param view        being the view of which picked.
     * @param i           being index of choice
     * @param l           amount of items inside the spinner.
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spinnerString = adapterView.getItemAtPosition(i).toString();
        spinnerPosition = adapterView.getSelectedItemPosition();

        Log.i(TAG, spinnerPosition + ": " + spinnerString);
    }

    /**
     * Unused.
     *
     * @param adapterView
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}