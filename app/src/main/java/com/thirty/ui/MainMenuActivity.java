package com.thirty.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 * Main Menu UI
 */
public class MainMenuActivity extends AppCompatActivity {

    /**
     * Creates Main Menu UI
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    /**
     * Inits the UI
     */
    private void initUI() {
        Button playGameButton = findViewById(R.id.button_start_game);
        playGameButton.setOnClickListener(view -> startGameView());
    }

    /**
     * Launches Game Activity.
     */
    private void startGameView() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
