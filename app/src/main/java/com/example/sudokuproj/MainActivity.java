package com.example.sudokuproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {
    public SudokuTable board;
    int mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button continueBut =findViewById(R.id.continue_button);
        //continueBut.setOnClickListener((View.OnClickListener) this);
        Button newEazyButton =findViewById(R.id.new_eazy_game);
        //newEazyButton.setOnClickListener((View.OnClickListener) this);
        Button newMedButton =findViewById(R.id.new_medium_game);
        //newMedButton.setOnClickListener((View.OnClickListener) this);
        //board = new SudokuTable();
        Spinner sp = findViewById(R.id.spin);
        spinCheck0(sp);
    }

    public void spinCheck0(View view){
        Spinner sp = findViewById(R.id.spin);
        mode = sp.getSelectedItemPosition();
        if (mode == 0){
            hideHard();
            takeEaz();
        }
        else
            takeHard();
        if (mode == 2){
            hideEaz();}
        else
            takeEaz();
    }
    public void spinCheck1(){
        Spinner sp = findViewById(R.id.spin);
        mode = sp.getSelectedItemPosition();
        if (mode == 0){
            hideHard();
            takeEaz();
        }
        else
            takeHard();
        if (mode == 2){
            hideEaz();}
        else
            takeEaz();
    }
    public void hideHard(){
        Button newEazyButton =findViewById(R.id.new_hard_game);
        newEazyButton.setVisibility(View.INVISIBLE);
    }
    public void hideEaz(){
        Button newEazyButton =findViewById(R.id.new_eazy_game);
        newEazyButton.setVisibility(View.INVISIBLE);
    }
    public void takeEaz(){
        Button newEazyButton =findViewById(R.id.new_eazy_game);
        newEazyButton.setVisibility(View.VISIBLE);
    }
    public void takeHard(){
        Button newEazyButton =findViewById(R.id.new_hard_game);
        newEazyButton.setVisibility(View.VISIBLE);
    }
    public void onClickCon(View view){
        Intent i;
        i = new Intent(this, GameSelf.class);
        spinCheck1();
        startActivity(i);
    }
    public void onClickEz(View view){
        Intent i;
        i = new Intent(this, GameSelf.class);
        i.putExtra("table", "Ez");
        spinCheck1();
        i.putExtra("mode", String.valueOf(mode));
        startActivity(i);
    }
    public void onClickMid(View view){
        Intent i;
        i = new Intent(this, GameSelf.class);
        i.putExtra("table", "Mid");
        spinCheck1();
        i.putExtra("mode", String.valueOf(mode));
        startActivity(i);
    }
    public void onClickHard(View view){
        Intent i;
        i = new Intent(this, GameSelf.class);
        i.putExtra("table", "Hd");
        spinCheck1();
        i.putExtra("mode", String.valueOf(mode));
        startActivity(i);
    }
}