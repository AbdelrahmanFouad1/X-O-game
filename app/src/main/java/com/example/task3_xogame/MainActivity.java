package com.example.task3_xogame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttons [][] = new Button[3][3];

    private boolean player1turn = true;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView  textViewResult1, textViewResult2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textViewResult1 = findViewById(R.id.result_1_tv);
        textViewResult2 = findViewById(R.id.result_2_tv);

        for (int i = 0 ; i < 3 ; i++ ){
            for (int j = 0 ; j < 3 ; j++ ){
                String buttonId = "btn_" + i + j ;
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                buttons [i][j] = findViewById(resId);
                buttons[i][j] .setOnClickListener(this);

            }
        }

        Button buttonReset = findViewById(R.id.reset_btn);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }

        });
    }

    @Override
    public void onClick(View view) {

        Button buttonView = (Button)view;
        if (!buttonView.getText().toString().equals("")){
            return;
        }

        if (player1turn){
            buttonView.setText("X");
        }else {
            buttonView.setText("O");
        }
        roundCount++;

        if (checkForWin()){
            if (player1turn){
                player1Wins();
            }else {
                player2Wins();
            }
        }else if (roundCount == 9){
            draw();
        }else {
            player1turn =! player1turn;
        }
    }
    //TODO: Difficult to understand Abdullah explains it.....................
    private boolean checkForWin () {

        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    private void player1Wins(){
        player1Points++;
        Toast.makeText(this, "Player 1 Win! ", Toast.LENGTH_SHORT).show();
        updatePointesText();
        resetBoard();
    }

    private void player2Wins(){
        player2Points++;
        Toast.makeText(this, "Player 2 Win! ", Toast.LENGTH_SHORT).show();
        updatePointesText();
        resetBoard();
    }

    private void draw(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointesText(){


        textViewResult1.setText(""+player1Points);
        textViewResult2.setText(""+ player2Points);
    }

    private void resetBoard(){

        for (int i = 0 ; i < 3 ; i++ ){
            for (int j = 0 ; j < 3 ; j++ ){
                buttons[i][j].setText("");
            }
        }
        roundCount = 0 ;
        player1turn = true;
    }
    private void resetGame(){
        player1Points = 0;
        player2Points = 0;
        updatePointesText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2points", player2Points);
        outState.putBoolean("player1turn", player1turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2points");
        player1turn = savedInstanceState.getBoolean("player1turn");
    }
}