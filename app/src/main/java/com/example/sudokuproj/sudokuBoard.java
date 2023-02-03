package com.example.sudokuproj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class sudokuBoard extends View {
    private final int boardColor;
    private final int cellFillColor;
    private final int cellsHighlightColor;

    private final Paint boardColorPaint = new Paint();
    private final Paint cellFillColorPaint = new Paint();
    private final Paint cellsHighlightColorPaint = new Paint();
    private int cellsize;

    private final SudokuTable board = new SudokuTable();
    public sudokuBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.sudokuBoard,
                0, 0);
        try {
            boardColor=a.getInteger(R.styleable.sudokuBoard_boardColor, 0);
            cellFillColor=a.getInteger(R.styleable.sudokuBoard_cellFillColor, 0);
            cellsHighlightColor=a.getInteger(R.styleable.sudokuBoard_cellsHighlightColor, 0);
        }
        finally
        {
            a.recycle();
        }
    }
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);

        int dimenton = Math.min(this.getMeasuredWidth(), this.getMeasuredHeight());
        cellsize = dimenton/9;
        setMeasuredDimension(dimenton, dimenton);

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

        //colorCell(canvas, board.getSelectedCollumn(), board.getSelectedRow());
        canvas.drawRect(0, 0, getWidth(), getHeight(), boardColorPaint);
        DrawBoard(canvas);
    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event){
        boolean isValid;
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN){
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
        if (board.getSelectedCollumn()!=-1 && board.getSelectedRow()!=-1){
            canvas.drawRect((c-1)*cellsize, 0, c*cellsize, cellsize*9,
                    cellsHighlightColorPaint);
            canvas.drawRect(0, (c-1)*cellsize, 9*cellsize, cellsize*r,
                    cellsHighlightColorPaint);
            canvas.drawRect((c-1)*cellsize, (c-1)*cellsize, c*cellsize,
                    cellsize*r, cellsHighlightColorPaint);
        }
        invalidate();
    }

    private void DrawThickLine(){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(10);
        boardColorPaint.setColor(0);
    }
    private void DrawThinLine(){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(4);
        boardColorPaint.setColor(0);
    }


    private void DrawBoard(Canvas canvas){
        for (int c = 0; c<10; c++){
            DrawThinLine();
            canvas.drawLine(0, 0,
                    20, 20, boardColorPaint);
        }
        for (int c = 0; c<10; c++){
            if (c%3==0)
                DrawThickLine();
            else
                DrawThinLine();
            canvas.drawLine(0, cellsize*c,
                    getMeasuredWidth(), cellsize*c, boardColorPaint);
        }
    }
}
