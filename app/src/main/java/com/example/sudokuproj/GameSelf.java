package com.example.sudokuproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class GameSelf extends AppCompatActivity {
    String difficulty, mode;
    sudokuBoard gameboard;
    SudokuTable tabl;
    int t, backeble;
    View back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences tablePref = getSharedPreferences("table", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_self);
        Bundle args = getIntent().getExtras();
        t=100000;
        backeble=1;
        Log.d("appWork", "Making");
        difficulty = args.getString("table");
        mode = args.getString("mode");

        tabl = new SudokuTable();
        View opn = findViewById(R.id.open);
        View hnt = findViewById(R.id.hints);
        gameboard=findViewById(R.id.sudokuBoard);
        Log.d("appWork", difficulty);
        if (mode.equals("2")) {
            opn.setVisibility(View.INVISIBLE);
            hnt.setVisibility(View.INVISIBLE);
            t = 0;
            gameboard.difficulty = 2;
        } else if (mode.equals("1")) {
            t = 3;
            hnt.setVisibility(View.INVISIBLE);
            gameboard.difficulty = 1;
        } else
            gameboard.difficulty = 0;
        back = findViewById(R.id.back);
        if (difficulty.equals("Cont")){
            Gson gson = new Gson();
            try {
                InputStream inputStream = openFileInput("table.txt");

                if (inputStream != null) {
                    InputStreamReader isr = new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(isr);
                    String line;
                    StringBuilder builder = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        builder.append(line + "\n");
                    }
                    String json = builder.toString();
                    Log.d("appWork", json);
                    inputStream.close();
                    gameboard = gson.fromJson(json, gameboard.getClass());
                    Log.d("appWork", "continuaing");
                }
            } catch (Throwable t) {
                Toast.makeText(getApplicationContext(),
                        "Exception: " + t.toString(),
                        Toast.LENGTH_LONG).show();
            }


        }
        else {
            if (difficulty.equals("Ez"))
            {
                Log.d("appWork", "EAZY");
                gameboard.getEasy();
                Log.d("appWork", "EAZY");
            }
            if (difficulty.equals("Mid"))
            {
                Log.d("appWork", "MIDDLE");
                gameboard.getMiddle();}
            if (difficulty.equals("Hd")){
                Log.d("appWork", "HARD");
                gameboard.getHard();}
        }
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
        /*
        Log.d("appWork", "BAKING");
        SharedPreferences tablePref = getSharedPreferences("table", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = tablePref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(gameboard);
        Log.d("appWork", "BAKING");
        try {
            OutputStream outputStream = openFileOutput("table.txt", 0);
            OutputStreamWriter osw = new OutputStreamWriter(outputStream);
            osw.write(json);
            osw.close();
        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(),
                    "Exception: " + t.toString(),
                    Toast.LENGTH_LONG).show();
        }
*/
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