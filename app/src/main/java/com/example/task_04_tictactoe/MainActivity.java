package com.example.task_04_tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button[] buttons;
    private boolean xTurn = true;
    private int roundCount = 0;
    private TextView statusTextView;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        statusTextView = findViewById(R.id.statusTextView);
        resetButton = findViewById(R.id.resetButton);

        buttons = new Button[9];
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            buttons[i] = (Button) gridLayout.getChildAt(i);
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onGridButtonClick((Button) v);
                }
            });
        }

        // Set up reset button
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    private void onGridButtonClick(Button button) {
        if (!button.getText().toString().equals("")) {
            return;
        }

        button.setText(xTurn ? "X" : "O");
        button.setTextColor(xTurn ? getResources().getColor(R.color.pinkAccent) : getResources().getColor(R.color.cyanAccent));
        roundCount++;

        if (checkForWin()) {
            if (xTurn) {
                statusTextView.setText("Winner: X");
            } else {
                statusTextView.setText("Winner: O");
            }
            disableButtons();
        } else if (roundCount == 9) {
            statusTextView.setText("Game Draw!");
        } else {
            xTurn = !xTurn;
            statusTextView.setText("Turn: " + (xTurn ? "X" : "O"));
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i * 3 + j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                return true;
            }
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return true;
        }

        return field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("");
    }

    private void resetGame() {
        roundCount = 0;
        xTurn = true;
        statusTextView.setText("Turn: X");

        for (Button button : buttons) {
            button.setText("");
            button.setEnabled(true);
        }
    }

    private void disableButtons() {
        for (Button button : buttons) {
            button.setEnabled(false);
        }
    }
}
