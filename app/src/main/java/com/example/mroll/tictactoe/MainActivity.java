package com.example.mroll.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int moveNum;
    final private int ROWLENGTH = 3;
    private Button[] buttons;
    private TextView winTextView;
    private int[] winConditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button1 = findViewById(R.id.button1);
        final Button button2 = findViewById(R.id.button2);
        final Button button3 = findViewById(R.id.button3);
        final Button button4 = findViewById(R.id.button4);
        final Button button5 = findViewById(R.id.button5);
        final Button button6 = findViewById(R.id.button6);
        final Button button7 = findViewById(R.id.button7);
        final Button button8 = findViewById(R.id.button8);
        final Button button9 = findViewById(R.id.button9);

        buttons = new Button[]{button1, button2, button3, button4, button5, button6, button7, button8, button9};

        for (int i = 0; i < buttons.length; i++)
        {
            buttons[i].setOnClickListener(buttonListener);
        }

        winTextView = findViewById(R.id.winTextView);

        if (savedInstanceState == null) {
            winConditions = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
            moveNum = 1;
        }
        else {
            winConditions = savedInstanceState.getIntArray("winConditions");
            moveNum = savedInstanceState.getInt("moveNum");

            for (int i = 0; i < winConditions.length; i++)
            {
                Log.d("winConditons", String.valueOf(winConditions[i]));
                if (winConditions[i] == 3 || winConditions[i] == -3)
                {
                    Log.d("anotherWinCondtions", "hello");
                    for (int j = 0; j < buttons.length; j++)
                    {
                        buttons[j].setClickable(false);
                    }
                    break;
                }
            }
        }

        final Button resetButton = findViewById(R.id.resetButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < buttons.length; i++)
                {
                    buttons[i].setText("");
                    buttons[i].setClickable(true);
                }
                for (int i = 0; i < winConditions.length; i++)
                {
                    winConditions[i] = 0;
                }
                winTextView.setText("");
                moveNum = 1;
            }
        });


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("moveNum", moveNum);
        outState.putIntArray("winConditions", winConditions);
    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button currentButton = (Button) v;
            currentButton.setClickable(false);
            TableRow row = (TableRow) v.getParent();

            int rowNum = Integer.parseInt((String) row.getTag());
            int colNum = Integer.parseInt((String) currentButton.getTag());
            // X turn to move on odd nums
            if (moveNum % 2 != 0)
            {
                currentButton.setText("X");
                moveNum += 1;
                // Fill out row win condition
                winConditions[rowNum] += 1;
                // Fill out column win condition
                winConditions[ROWLENGTH + colNum] += 1;
                // Fill out diagonal win condition
                if (rowNum == colNum)
                {
                    winConditions[2*ROWLENGTH] += 1;
                }
                // Fill out anti-diagonal win condition
                if (ROWLENGTH - 1 - colNum == rowNum)
                {
                    winConditions[2*ROWLENGTH + 1] += 1;
                }
            }
            // O turn to move on even nums
            else {
                currentButton.setText("O");
                moveNum += 1;
                // Fill out row win condition
                winConditions[rowNum] -= 1;
                // Fill out column win condition
                winConditions[ROWLENGTH + colNum] -= 1;
                // Fill out diagonal win condition
                if (rowNum == colNum)
                {
                    winConditions[2*ROWLENGTH] -= 1;
                }
                // Fill out anti-diagonal win condition
                if (ROWLENGTH - 1 - colNum == rowNum)
                {
                    winConditions[2*ROWLENGTH + 1] -= 1;
                }
            }

            for (int i = 0; i < winConditions.length; i++)
            {
                if (winConditions[i] == 3)
                {
                    winTextView.setText("Player 1 wins");
                    for (int j = 0; j < buttons.length; j++)
                    {
                        buttons[j].setClickable(false);
                    }
                    break;
                }
                else if (winConditions[i] == -3)
                {
                    winTextView.setText("Player 2 wins");
                    for (int j = 0; j < buttons.length; j++)
                    {
                        buttons[j].setClickable(false);
                    }
                    break;
                }
            }

            // Now it is move 10 so all spaces filled && No other win condition is met
            if (moveNum == 10 && winTextView.getText().equals(""))
            {
                winTextView.setText("Draw");
            }

        }
    };
}
