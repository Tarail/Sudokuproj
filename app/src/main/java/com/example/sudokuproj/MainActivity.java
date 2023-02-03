package com.example.sudokuproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public SudokuTable board;
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
    }

    public void onClickCon(View view){
        Intent i;
        i = new Intent(this, GameSelf.class);
        startActivity(i);
    }
    public void onClickEz(View view){
        Intent i;
        i = new Intent(this, GameSelf.class);
        startActivity(i);
    }
    public void onClickMed(View view){
        Intent i;
        i = new Intent(this, GameSelf.class);
        startActivity(i);
    }
}