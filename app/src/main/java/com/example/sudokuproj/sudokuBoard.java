package com.example.sudokuproj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class sudokuBoard extends View {
    private final int boardColor;
    private final int cellFillColor;
    private final int cellsHighlightColor;
    public boolean pensil;
    public boolean hint;
    public int difficulty;

    private final Paint boardColorPaint = new Paint();
    private final Paint cellFillColorPaint = new Paint();
    private final Paint cellsHighlightColorPaint = new Paint();
    private final Paint letterPaint = new Paint();
    private final Rect letterPaintBounds = new Rect();
    private final Rect miniletterPaintBounds = new Rect();
    private int cellsize;
    private int[][] cur;
    private int[][] base;
    private int[][][] pnsl;
    private int selectedX, selectedY;


    public SudokuTable board = new SudokuTable();
    public sudokuBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        cur = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}};
        cur = new int[][]{{1, 2, 3, 4, 5, 6, 7, 8, 9}, {7, 8, 9, 1, 2, 3, 4, 5, 6}, {4, 5, 6, 7, 8, 9, 1, 2, 3},
                {9, 1, 2, 3, 4, 5, 6, 7, 8}, { 6, 7, 8, 9, 1, 2, 3, 4, 5}, {3, 4, 5, 6, 7, 8, 9, 1, 2},
                {8, 9, 1, 2, 3, 4, 5, 6, 7}, {5, 6, 7, 8, 9, 1, 2, 3, 4}, {2, 3, 4, 5, 6, 7, 8, 9, 1}};
        pnsl = new int[9][9][9];
        startPnsl();
        pensil = false;
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.sudokuBoard,
                0, 0);
        try {
            hint=false;
            board.makeEazy();
            board.reroll();
            boardColor=a.getInteger(R.styleable.sudokuBoard_boardColor, 0);
            cellFillColor=a.getInteger(R.styleable.sudokuBoard_cellFillColor, 0);
            cellsHighlightColor=a.getInteger(R.styleable.sudokuBoard_cellsHighlightColor, 0);
        }
        finally
        {
            a.recycle();
        }
    }
    public void startPnsl(){
        for (int i=0; i<9; i++){
            for (int ii=0; ii<9; ii++){
                for (int iii=0; iii<9; iii++){
                    pnsl[i][ii][iii]=0;
                }
            }
        }
    }
    public void setBoard(SudokuTable a){
        board.copyBoard(a);
    }
    public SudokuTable getBoard(){
        return board;
    }
    public void getEasy(){
        board.makeEazy();
    }
    public void getMiddle(){
        board.makeMiddle();
    }

    public void setSelectedNum(int a){
        if (board.gen[selectedX][selectedY]!=0)
            return;
        if (selectedY==-1 || selectedX==-1)
            return;
        if (board.cur[selectedX][selectedY]==a) {
            board.cur[selectedX][selectedY]=0;
            return;
        }
        if (board.cur[selectedX][selectedY]==0 && pensil){
            pnsl[selectedX][selectedY][a-1]=a;
            return;
        }
        board.cur[selectedX][selectedY]=a;
        pnsl[selectedX][selectedY]=new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        if (board.correct[selectedX][selectedY]==a)
            board.lastCorrect[selectedX][selectedY]=a;
    }

    public void returnToLastCorrect(){
        board.cur=board.transp(board.lastCorrect);
    }
    public void openSelected(){
        if (selectedY==-1 || selectedX==-1)
            return;
        board.gen[selectedX][selectedY]=board.correct[selectedX][selectedY];
        board.cur[selectedX][selectedY]=board.correct[selectedX][selectedY];
        board.lastCorrect[selectedX][selectedY]=board.correct[selectedX][selectedY];
    }
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);

        int dimenton = Math.min(this.getMeasuredWidth(), this.getMeasuredHeight());
        cellsize = dimenton/9;
        setMeasuredDimension(dimenton, dimenton);
        Log.d("mytag", "***");

    }
    protected void onDraw(Canvas canvas){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(16);
        boardColorPaint.setColor(boardColor);
        boardColorPaint.setAntiAlias(true);

        cellFillColorPaint.setStyle(Paint.Style.FILL);
        boardColorPaint.setAntiAlias(true);
        cellFillColorPaint.setColor(cellFillColor);

        cellsHighlightColorPaint.setStyle(Paint.Style.FILL);
        boardColorPaint.setAntiAlias(true);
        cellsHighlightColorPaint.setColor(cellsHighlightColor);


        colorCell(canvas, board.getSelectedCollumn(), board.getSelectedRow());
        Log.d("mytag", "Before_Rect");
        canvas.drawRect(0, 0, getWidth(), getHeight(), boardColorPaint);
        Log.d("mytag", "After_Rect");
        DrawBoard(canvas);
        DrawNumbers(canvas);
        if (hint)
            DrawMiniNumbers(canvas);
        DrawPensil(canvas);
        Log.d("mytag", "Done");
    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event){
        Log.d("appWork", "Touch");
        boolean isValid;
        float x = event.getX();
        float y = event.getY();
        selectedX = (int)Math.ceil(y/cellsize) - 1;
        selectedY = (int)Math.ceil(x/cellsize) - 1;
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN){
            Log.d("mytagTouch", "After_Rect");
            board.setSelectedCollumn((int)Math.ceil(y/cellsize));
            board.setSelectedRow((int)Math.ceil(x/cellsize));
            isValid=true;
        }
        else {
            isValid=false;
        }

        return isValid;
    }

    private void colorCell(Canvas canvas, int r, int c){
        if (c!=-1 && r!=-1){
            cellsHighlightColorPaint.setColor(Color.parseColor("#D9FFFF"));
            canvas.drawRect((c-1)*cellsize, 0, c*cellsize, cellsize*9,
                    cellsHighlightColorPaint);
            canvas.drawRect(0, (r-1)*cellsize, 9*cellsize, cellsize*r,
                    cellsHighlightColorPaint);
            cellsHighlightColorPaint.setColor(Color.parseColor("#AAEEEE"));
            canvas.drawRect((c-1)*cellsize, (r-1)*cellsize, c*cellsize,
                    cellsize*r, cellsHighlightColorPaint);
        }
        invalidate();
    }

    private void DrawThickLine(){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(12);
        boardColorPaint.setColor(Color.parseColor("#000000"));
        Log.d("mytag", "Thick_line");
    }
    private void DrawThinLine(){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(6);
        boardColorPaint.setColor(Color.parseColor("#000000"));
        Log.d("mytag", "Thin_line");
    }


    private void DrawBoard(Canvas canvas){
        //canvas.drawColor(Color.WHITE);
        for (int c = 0; c<10; c++){
            if (c%3==0)
                DrawThickLine();
            else
                DrawThinLine();
            canvas.drawLine(cellsize*c, 0,
                    cellsize*c, getMeasuredWidth(), boardColorPaint);
        }
        Log.d("mytag", "Vertical_Drawn");

        for (int c = 0; c<10; c++){
            if (c%3==0)
                DrawThickLine();
            else
                DrawThinLine();
            canvas.drawLine(0, cellsize*c,
                    getMeasuredWidth(), cellsize*c, boardColorPaint);
        }

        Log.d("mytag", "Horizontal_Drawn");
    }

    private void DrawNumbers(Canvas canvas){
        //canvas.drawColor(Color.WHITE);
        letterPaint.setTextSize(cellsize);
        float height, width;

        for (int c = 1; c<10; c++){
            for (int cc = 1; cc<10; cc++){
                int theNum = board.cur[c-1][cc-1];
                if (theNum==0)
                    continue;
                String text = Integer.toString(theNum);
                Log.d("mytag", text);
                if (board.cur[c-1][cc-1]!=board.correct[c-1][cc-1] && difficulty!=2)
                    letterPaint.setColor(Color.RED);
                else
                if (board.cur[c-1][cc-1]==board.gen[c-1][cc-1])
                    letterPaint.setColor(Color.BLACK);
                else
                    letterPaint.setColor(Color.parseColor("#994499"));
                letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                width=letterPaint.measureText(text);
                height=letterPaintBounds.height();
                canvas.drawText(text, ((cc-1)*cellsize + (cellsize-width)/2), (c*cellsize - (cellsize-height)/2), letterPaint);
            }
        }
        Log.d("mytag", "Numbers");
    }

    private void DrawMiniNumbers(Canvas canvas){
        //canvas.drawColor(Color.WHITE);
        letterPaint.setTextSize(cellsize/100*32);
        for (int c = 1; c<10; c++){
            for (int cc = 1; cc<10; cc++){
                int theNum = board.cur[c-1][cc-1];
                if (theNum!=0)
                    continue;
                int[] gg = board.getCommonCell(board.cur, c-1, cc-1);
                for (int i = 0; i < 9; i++){
                    if (gg[i]==0)
                        continue;
                    int num = gg[i];
                    int qazyNum = num - 1;
                    String text = Integer.toString(num);
                    letterPaint.setColor(Color.GRAY);
                    letterPaint.getTextBounds(text, 0, text.length(), miniletterPaintBounds);
                    int numy = ((qazyNum/3) - 2) * (cellsize/3);
                    int numx = (qazyNum%3) * (cellsize/3);
                    canvas.drawText(text, ((cc-1)*cellsize + numx + cellsize/16), (c*cellsize + numy - cellsize/22), letterPaint);
                }
            }
        }
        Log.d("mytag", "Numbers");
    }
    private void DrawPensil(Canvas canvas){
        letterPaint.setTextSize(cellsize/100*32);
        for (int c = 1; c<10; c++){
            for (int cc = 1; cc<10; cc++){
                int theNum = board.cur[c-1][cc-1];
                if (theNum!=0)
                    continue;
                for (int i = 0; i < 9; i++){
                    if (pnsl[c-1][cc-1][i]==0)
                        continue;
                    int num = pnsl[c-1][cc-1][i];
                    int qazyNum = num - 1;
                    String text = Integer.toString(num);
                    letterPaint.setColor(Color.GRAY);
                    letterPaint.getTextBounds(text, 0, text.length(), miniletterPaintBounds);
                    int numy = ((qazyNum/3) - 2) * (cellsize/3);
                    int numx = (qazyNum%3) * (cellsize/3);
                    canvas.drawText(text, ((cc-1)*cellsize + numx + cellsize/16), (c*cellsize + numy - cellsize/22), letterPaint);
                }
            }
        }
        Log.d("mytag", "Numbers");
    }
}