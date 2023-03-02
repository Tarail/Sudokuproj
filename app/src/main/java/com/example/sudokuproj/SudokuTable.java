package com.example.sudokuproj;

import java.util.ArrayList;
import java.util.List;

public class SudokuTable {
    int selectedRow, selectedCollumn;
    int[][] cur, correct, lastCorrect, gen, pensil;
    int[] numbers;
    public SudokuTable(){
        selectedRow=-1;
        selectedCollumn=-1;

        gen = new int[][]{{1, 2, 3, 4, 5, 6, 7, 8, 9}, {7, 8, 9, 1, 2, 3, 4, 5, 6}, {4, 5, 6, 7, 8, 9, 1, 2, 3},
                {9, 1, 2, 3, 4, 5, 6, 7, 8}, { 6, 7, 8, 9, 1, 2, 3, 4, 5}, {3, 4, 5, 6, 7, 8, 9, 1, 2},
                {8, 9, 1, 2, 3, 4, 5, 6, 7}, {5, 6, 7, 8, 9, 1, 2, 3, 4}, {2, 3, 4, 5, 6, 7, 8, 9, 1}};
        numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

        generateStart();
    }
    public void printNums(){
        for (int i=0; i<9; i++){
            System.out.print(numbers[i] + " ");
        }
        System.out.println();
    }
    public void copyBoard(SudokuTable tbl){
        this.gen=tbl.gen;
        this.correct=tbl.correct;
        this.cur=tbl.cur;
        this.lastCorrect=tbl.lastCorrect;
    }
    public int getSelectedRow(){
        return selectedRow;
    }
    public int getSelectedCollumn(){
        return selectedCollumn;
    }
    public void setSelectedRow(int r){
        selectedRow=r;
    }
    public void setSelectedCollumn(int c){
        selectedCollumn=c;
    }
    public int[][] swapTable(int[][] a){
        int[][] table=a;
        for (int i=0; i<9; i++)
            for (int j=0; j<9; j++){
                if (j>i){
                    int gs = table[i][j];
                    table[i][j]=a[j][i];
                    a[j][i]=gs;
                }

            }
        return table;
    }

    public void generateStart(){
        for (int i=0; i<50; i++) {
            int a1 = (int) (Math.random() * 9);
            int a2 = (int) (Math.random() * 9);
            if (a1==a2)
                continue;
            numbers[a1] += numbers[a2];
            numbers[a2] = numbers[a1] - numbers[a2];
            numbers[a1] -= numbers[a2];
        }
        for (int j=0; j<3; j++) {
            for (int i=0; i<10; i++){
                int a1 = (int) (Math.random() * 3);
                int a2 = (int) (Math.random() * 3);
                if (a1==a2)
                    continue;
                int[] h=gen[j*3+a1];
                gen[j*3+a1]=gen[j*3+a2];
                gen[j*3+a2]=h;
            }}
        gen = swapTable(gen);
        for (int j=0; j<3; j++) {
            for (int i=0; i<10; i++){
                int a1 = (int) (Math.random() * 3);
                int a2 = (int) (Math.random() * 3);
                if (a1==a2)
                    continue;
                int[] h=gen[j*3+a1];
                gen[j*3+a1]=gen[j*3+a2];
                gen[j*3+a2]=h;
            }}
        gen = swapTable(gen);
        for (int j=0; j<3; j++) {
            int a1 = (int) (Math.random() * 3);
            int a2 = (int) (Math.random() * 3);
            if (a1==a2)
                continue;
            for (int i=0; i<3; i++){
                int[] h=gen[i+a1*3];
                gen[i+a1*3]=gen[i+a2*3];
                gen[i+a2*3]=h;
            }}
        gen = swapTable(gen);
        for (int j=0; j<3; j++) {
            int a1 = (int) (Math.random() * 3);
            int a2 = (int) (Math.random() * 3);
            if (a1==a2)
                continue;
            for (int i=0; i<3; i++){
                int[] h=gen[i+a1*3];
                gen[i+a1*3]=gen[i+a2*3];
                gen[i+a2*3]=h;
            }}
        gen = swapTable(gen);
        for (int i=0; i<9; i++)
            for (int j=0; j<9; j++){
                gen[i][j]=numbers[gen[i][j]-1];
            }
    }
    public void reroll(){
        for (int j=0; j<3; j++) {
            for (int i=0; i<10; i++){
                int a1 = (int) (Math.random() * 3);
                int a2 = (int) (Math.random() * 3);
                if (a1==a2)
                    continue;
                int[] h=gen[j*3+a1];
                gen[j*3+a1]=gen[j*3+a2];
                gen[j*3+a2]=h;

                h=correct[j*3+a1];
                correct[j*3+a1]=correct[j*3+a2];
                correct[j*3+a2]=h;
            }}
        correct = swapTable(correct);
        gen = swapTable(gen);
        for (int j=0; j<3; j++) {
            for (int i=0; i<10; i++){
                int a1 = (int) (Math.random() * 3);
                int a2 = (int) (Math.random() * 3);
                if (a1==a2)
                    continue;
                int[] h=gen[j*3+a1];
                gen[j*3+a1]=gen[j*3+a2];
                gen[j*3+a2]=h;

                h=correct[j*3+a1];
                correct[j*3+a1]=correct[j*3+a2];
                correct[j*3+a2]=h;
            }}
        correct = swapTable(correct);
        gen = swapTable(gen);
        for (int j=0; j<3; j++) {
            int a1 = (int) (Math.random() * 3);
            int a2 = (int) (Math.random() * 3);
            if (a1==a2)
                continue;
            for (int i=0; i<3; i++){
                int[] h=gen[i+a1*3];
                gen[i+a1*3]=gen[i+a2*3];
                gen[i+a2*3]=h;

                h=correct[i+a1*3];
                correct[i+a1*3]=correct[i+a2*3];
                correct[i+a2*3]=h;
            }}
        correct = swapTable(correct);
        gen = swapTable(gen);
        for (int j=0; j<3; j++) {
            int a1 = (int) (Math.random() * 3);
            int a2 = (int) (Math.random() * 3);
            if (a1==a2)
                continue;
            for (int i=0; i<3; i++){
                int[] h=gen[i+a1*3];
                gen[i+a1*3]=gen[i+a2*3];
                gen[i+a2*3]=h;

                h=correct[i+a1*3];
                correct[i+a1*3]=correct[i+a2*3];
                correct[i+a2*3]=h;
            }}
        correct = swapTable(correct);
        gen = swapTable(gen);
        lastCorrect=transp(gen);
        cur=transp(gen);
    }
    public int getVariants(int[][] g){
        int zeroes=0;
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                if (g[i][j]==0)
                    zeroes++;
            }
        }
        if (zeroes==0)
            return 1;
        int count=0, a=-1, b=-1;
        List<Integer> minCell=new ArrayList<Integer>();
        for (int i=0; i<9; i++)
            minCell.add(i);
        List<Integer> Cell;
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                if (g[i][j]==0){
                    Cell=getCell(g, i ,j);
                    if (Cell.size()<minCell.size())
                    {
                        a=i;
                        b=j;
                        minCell=Cell;
                    }
                }
            }
        }
        if (a==-1)
            return 0;
        for (int i=0; i<minCell.size(); i++)
        {
            g[a][b]=minCell.get(i);
            count+=getVariants(g);
        }

        return count;
    }
    public List<Integer> getCell(int[][] g, int a, int b){
        //System.out.println(a + " " + b);
        int[] mVars={1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i=0; i<9; i++){
            if (i==b)
                continue;
            if (g[a][i]!=0)
                mVars[g[a][i]-1]=0;
        }

        for (int i=0; i<9; i++){
            if (i==a)
                continue;
            if (g[i][b]!=0)
                mVars[g[i][b]-1]=0;
        }

        int thisI=a/3, thisJ=b/3;
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++){
                if (a==i+thisI*3 && j==b+thisJ*3)
                    continue;
                if (g[i+thisI*3][j+thisJ*3]==0)
                    continue;
                mVars[g[i+thisI*3][j+thisJ*3]-1]=0;
            }
        List<Integer> lis=new ArrayList<Integer>();
        for (int i=0; i<9; i++)
            if (mVars[i]!=0)
                lis.add(mVars[i]);
        return lis;
    }
    public int[] getCommonCell(int[][] g, int a, int b){
        //System.out.println(a + " " + b);
        int[] mVars={1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i=0; i<9; i++){
            if (i==b)
                continue;
            if (g[a][i]!=0)
                mVars[g[a][i]-1]=0;
        }
        for (int i=0; i<9; i++){
            if (i==a)
                continue;
            if (g[i][b]!=0)
                mVars[g[i][b]-1]=0;
        }
        int thisI=a/3, thisJ=b/3;
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++){
                if (a==i+thisI*3 && j==b+thisJ*3)
                    continue;
                if (g[i+thisI*3][j+thisJ*3]==0)
                    continue;
                mVars[g[i+thisI*3][j+thisJ*3]-1]=0;
            }

        return mVars;
    }
    public void makeEazy(){
        correct=transp(gen);
        int a;
        int b;
        for (int i=0; i < 9; i++){
            int h = i/3;
            int w = i%3;
            a=(int) (Math.random()*3);
            b=(int) (Math.random()*3);
            gen[h*3+b][w*3+a] = 0;
        }
        int t=(int) (Math.random()*10)+15;

        for (int i=0; i<10; i++){
            a=(int) (Math.random()*9);
            b=(int) (Math.random()*9);
            gen[a][b]=0;
        }
        while (t>0){
            int h=0;
            for (int i=0; i<9; i++){
                for (int j=0; j<9; j++){
                    if (gen[i][j]!=0 && getCell(gen, i, j).size()==1)
                    {
                        gen[i][j]=0;
                        h=1;
                        break;
                    }
                    if (h==1)
                        break;
                }
            }
            t--;
            if (h==0)
                break;
        }
        cur=transp(gen);
        lastCorrect=transp(gen);
    }
    public void makeMiddle(){
        correct=transp(gen);
        int a;
        int b;
        for (int i=0; i < 9; i++){
            int h = i/3;
            int w = i%3;
            a=(int) (Math.random()*3);
            b=(int) (Math.random()*3);
            gen[h*3+b][w*3+a] = 0;
        }
        for (int i=0; i<15; i++){
            a=(int) (Math.random()*9);
            b=(int) (Math.random()*9);
            gen[a][b]=0;
        }
        int aj=0;
        while (true){
            aj++;
            int h=0;
            for (int i=0; i<9; i++){
                for (int j=0; j<9; j++){
                    if (gen[i][j]!=0 && getCell(gen, i, j).size()==1)
                    {
                        gen[i][j]=0;
                        h=1;
                        //System.out.println(i+" "+j);
                    }
                }
            }
            if (h==0)
                break;
        }
        cur=transp(gen);
        lastCorrect=transp(gen);
    }

    public int[][] transp(int[][] h){
        int[][] l=new int[9][9];
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++)
            {
                l[i][j]=h[i][j];
            }
        }
        return l;
    }

    public void save(){

    }
    public void load(){

    }
}
