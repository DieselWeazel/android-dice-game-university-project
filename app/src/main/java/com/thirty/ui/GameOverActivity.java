package com.thirty.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

/**
 * UI For handling the Game Over process, show user the total score, and offer a rematch.
 */
public class GameOverActivity extends AppCompatActivity {

    protected static final String TOTAL_GAME_POINT_STRING_KEY = "com.thirty.ui.TOTAL_GAME_POINT_STRING_KEY";
    protected static final String ROUND_HISTORY_STRING_KEY = "com.thirty.ui.ROUND_HISTORY_STRING_KEY";

    private String collectedGameScoreString;
    private String totalGamePointString;

    private TextView textView_totalScore;
    private TextView textView_entireRoundHistory;
    private Button playAgainButton;

    /**
     * Creates UI
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_screen);
        textView_totalScore = findViewById(R.id.text_view_totalscore);
        textView_entireRoundHistory = findViewById(R.id.text_view_round_array_score);
        playAgainButton = findViewById(R.id.button_start_game);

        Intent intent = getIntent();

        if (savedInstanceState != null) {
            totalGamePointString = savedInstanceState.getString(TOTAL_GAME_POINT_STRING_KEY);
            collectedGameScoreString = savedInstanceState.getString(ROUND_HISTORY_STRING_KEY);
        } else {
            totalGamePointString = intent.getStringExtra(GameActivity.TOTAL_GAME_POINTS_KEY);
            String[] resultRoundStringList = intent.getStringArrayExtra(GameActivity.ROUND_RESULT_STRING_ARRAY_KEY);

            collectedGameScoreString = "";
            for (int i = 0; i < resultRoundStringList.length; i++) {
                collectedGameScoreString += resultRoundStringList[i];
            }

        }
        playAgainButton.setOnClickListener(e -> {
            Intent newIntent = new Intent(this, GameActivity.class);
            startActivity(newIntent);
            finish();
        });
        textView_totalScore.setText(totalGamePointString);
        textView_entireRoundHistory.setText(collectedGameScoreString);
    }

    /**
     * If User rotates we save the values given.
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TOTAL_GAME_POINT_STRING_KEY, totalGamePointString);
        outState.putString(ROUND_HISTORY_STRING_KEY, collectedGameScoreString);
    }

}
