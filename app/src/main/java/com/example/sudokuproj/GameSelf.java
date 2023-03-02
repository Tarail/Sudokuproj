package com.example.sudokuproj;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class GameSelf extends AppCompatActivity {
    String difficulty, mode;
    sudokuBoard gameboard;
    SudokuTable tabl;
    int t, backeble;
    View back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_self);
        Bundle args = getIntent().getExtras();
        t=100000;
        backeble=1;
        Log.d("appWork", "Making");
        difficulty = args.getString("table");
        mode = args.getString("mode");
        View opn = findViewById(R.id.open);
        View hnt = findViewById(R.id.hints);
        gameboard=findViewById(R.id.sudokuBoard);

        if (mode.equals("2")){
            opn.setVisibility(View.INVISIBLE);
            hnt.setVisibility(View.INVISIBLE);
            t=0;
            gameboard.difficulty=2;
        }
        else
            if (mode.equals("1")){
                t=3;
                hnt.setVisibility(View.INVISIBLE);
                gameboard.difficulty=1;
            }
            else
                gameboard.difficulty=0;
        String tt = String.valueOf(t);
        back = findViewById(R.id.back);

        tabl = gameboard.board;
        if (difficulty=="Ez")
            tabl.makeEazy();
        if (difficulty=="Mid")
            tabl.makeMiddle();
        if (difficulty=="Hd")
            tabl.makeMiddle();
        //tabl.cur=tabl.correct;
        Log.d("appWork", "done");
    }

    public void open(View view){

        if (t==0)
            return;
        if (mode.equals("1")){
            t--;
            Log.d("appWork", String.valueOf(t));
            if (t==2){
                Log.d("appWork", "open");
                View opn = findViewById(R.id.open);
                opn.setBackgroundColor(Color.parseColor("#44B0C4"));
            }
            if (t==1){
                View opn = findViewById(R.id.open);
                opn.setBackgroundColor(Color.parseColor("#88A0B0"));
            }
            if (t==0){
                View opn = findViewById(R.id.open);
                opn.setBackgroundColor(Color.RED);
            }
        }
        gameboard.openSelected();
        gameboard.invalidate();
    }
    public void hin(View view){
        if (gameboard.hint){
            gameboard.hint=false;
            View hnt = findViewById(R.id.hints);
            hnt.setBackgroundColor(Color.parseColor("#44DD44"));
        }
        else{
            gameboard.hint=true;
            View hnt = findViewById(R.id.hints);
            hnt.setBackgroundColor(Color.RED);
        }
        gameboard.invalidate();
    }
    public void takePutPensil(View view){
        if (gameboard.pensil) {
            gameboard.pensil=false;
            view.setBackgroundColor(Color.parseColor("#00BCD4"));
        }
        else {
            gameboard.pensil=true;
            view.setBackgroundColor(Color.GREEN);

        }
    }
    public void back(View view){
        Log.d("appWork", "back");
        if (backeble==0)
            return;
        if (mode.equals("2"))
            backeble=0;
        Log.d("appWork", "back3");
        View bck = findViewById(R.id.back);
        bck.setBackgroundColor(Color.RED);
        if (gameboard.difficulty==2)
            backeble=0;
        gameboard.board.cur=gameboard.board.transp(gameboard.board.lastCorrect);
        gameboard.board.gen=gameboard.board.transp(gameboard.board.lastCorrect);
        gameboard.invalidate();
    }
    public void backInMenu(View view){
        finish();
        return;
    }
    public void OnePress(View view){
        gameboard.setSelectedNum(1);
        gameboard.invalidate();
    }
    public void TwoPress(View view){
        gameboard.setSelectedNum(2);
        gameboard.invalidate();
    }
    public void ThreePress(View view){
        gameboard.setSelectedNum(3);
        gameboard.invalidate();
    }
    public void FourPress(View view){
        gameboard.setSelectedNum(4);
        gameboard.invalidate();
    }
    public void FivePress(View view){
        gameboard.setSelectedNum(5);
        gameboard.invalidate();
    }
    public void SixPress(View view){
        gameboard.setSelectedNum(6);
        gameboard.invalidate();
    }
    public void SevenPress(View view){
        gameboard.setSelectedNum(7);
        gameboard.invalidate();
    }
    public void EightPress(View view){
        gameboard.setSelectedNum(8);
        gameboard.invalidate();
    }
    public void NinePress(View view){
        gameboard.setSelectedNum(9);
        gameboard.invalidate();
    }


}