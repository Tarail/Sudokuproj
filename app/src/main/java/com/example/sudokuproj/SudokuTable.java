package com.example.sudokuproj;

import java.util.ArrayList;
import java.util.List;
import java.util.BitSet;

public class SudokuTable {

    int selectedRow, selectedCollumn, size;
    int[][] cur, correct, lastCorrect, gen, pensil;
    int [] graph;
    int[] numbers;
    int [] weight;
    int graphsize;
    int [] connects;
    ArrayList<Integer> bunchTips;
    ArrayList<Integer> currentTips;
    ArrayList<BitSet> currentCliks;
    int[][] connections;
    ArrayList<Integer> curg;

    public SudokuTable(){
        selectedRow=-1;
        selectedCollumn=-1;

        gen = new int[][]{  {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {7, 8, 9, 1, 2, 3, 4, 5, 6},
                {4, 5, 6, 7, 8, 9, 1, 2, 3},
                {9, 1, 2, 3, 4, 5, 6, 7, 8},
                {6, 7, 8, 9, 1, 2, 3, 4, 5}, {3, 4, 5, 6, 7, 8, 9, 1, 2},
                {8, 9, 1, 2, 3, 4, 5, 6, 7}, {5, 6, 7, 8, 9, 1, 2, 3, 4}, {2, 3, 4, 5, 6, 7, 8, 9, 1}};
        numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        generateFinal(3);
    }

    public SudokuTable(int count){
        size=count;
        gen = new int[count*count][count*count];
        selectedRow=-1;
        selectedCollumn=-1;
        for (int i = 0; i < count; i++){
            for (int i1 = 0; i1 < count; i1++){
                for (int ii = 0; ii < count * count; ii++){
                    gen[i+i1*count][ii]=(ii+(i1+count*i)) % (count*count) + 1;
                }
            }
        }
        numbers = new int[count*count];
        for (int i = 0; i < count*count; i++){
            numbers[i]=i+1;
        }
        //printTable();
        generateFinal(count);
    }

    public void printNums(){
        for (int i=0; i<size*size; i++){
            System.out.print(numbers[i] + " ");
        }
        System.out.println();
    }

    public void printTable(){
        for (int i=0; i<size*size; i++){
            for (int j=0; j<size*size; j++){
                System.out.print(gen[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printTable(int [][] g){
        for (int i=0; i<size*size; i++){
            for (int j=0; j<size*size; j++){
                System.out.print(g[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void copyBoard(SudokuTable tbl){
        this.size=tbl.size;
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

    public int[][] swapTable(int[][] a, int g){
        int[][] table=a;
        for (int i=0; i<g; i++)
            for (int j=0; j<g; j++){
                if (j>i){
                    int gs = table[i][j];
                    table[i][j]=a[j][i];
                    a[j][i]=gs;
                }

            }
        return table;
    }

    public void generateFinal(int count){
        int count2 = count*count;
        for (int i=0; i<count2*count2; i++) {
            int a1 = (int) (Math.random() * count2);
            int a2 = (int) (Math.random() * count2);
            if (a1==a2)
                continue;
            numbers[a1] += numbers[a2];
            numbers[a2] = numbers[a1] - numbers[a2];
            numbers[a1] -= numbers[a2];
        }
        for (int j=0; j<count; j++) {
            for (int i=0; i<=count2; i++){
                int a1 = (int) (Math.random() * count);
                int a2 = (int) (Math.random() * count);
                if (a1==a2)
                    continue;
                int[] h=gen[j*count+a1];
                gen[j*count+a1]=gen[j*count+a2];
                gen[j*count+a2]=h;
            }}
        gen = swapTable(gen, count2);
        for (int j=0; j<count; j++) {
            for (int i=0; i<=count2; i++){
                int a1 = (int) (Math.random() * count);
                int a2 = (int) (Math.random() * count);
                if (a1==a2)
                    continue;
                int[] h=gen[j*count+a1];
                gen[j*count+a1]=gen[j*count+a2];
                gen[j*count+a2]=h;
            }}
        gen = swapTable(gen, count2);
        for (int j=0; j<count; j++) {
            int a1 = (int) (Math.random() * count);
            int a2 = (int) (Math.random() * count);
            if (a1==a2)
                continue;
            for (int i=0; i<count; i++){
                int[] h=gen[i+a1*count];
                gen[i+a1*count]=gen[i+a2*count];
                gen[i+a2*count]=h;
            }}
        gen = swapTable(gen, count2);
        for (int j=0; j<count; j++) {
            int a1 = (int) (Math.random() * count);
            int a2 = (int) (Math.random() * count);
            if (a1==a2)
                continue;
            for (int i=0; i<count; i++){
                int[] h=gen[i+a1* count];
                gen[i+a1*count]=gen[i + a2 * count];
                gen[i+a2*count]=h;
            }}
        gen = swapTable(gen, count2);
        for (int i=0; i<count2; i++)
            for (int j=0; j<count2; j++){
                gen[i][j]=numbers[gen[i][j]-1];
            }

        int counter = 0, counter2 = 0;
        weight = new int[count2*count2*count2];
        for (int i=0; i<count2; i++){
            for (int j=0; j<count2; j++){
                int nn=gen[i][j];
                for (int z=0; z<count2; z++){
                    if (z==nn-1){
                        counter2++;
                        weight[i*count2*count2 + j*count2 + z]=1;
                        //System.out.println(i + " " + j + " " + z);
                    }
                    else{
                        counter++;
                        weight[i*count2*count2 + j*count2 + z]=4;}
                }
            }
        }
    }

    void setCell(int a, int b, int num){
        gen[a][b]=num;
        int bigsize = size*size;
        num--;
        for (int i=0; i<size*size; i++){
            weight[a*bigsize*bigsize + b*bigsize + i]++;
        }
        for (int i=0; i<size*size; i++){
            weight[i*bigsize*bigsize + b*bigsize + num]++;
        }
        for (int i=0; i<size*size; i++){
            weight[a*bigsize*bigsize + i*bigsize + num]++;
        }

        int aa=a/size, bb=b/size;
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                weight[(aa*size+i)*bigsize*bigsize + (bb*size+j)*bigsize + num]++;
            }
        }
        weight[a*bigsize*bigsize + b*bigsize + num]=1;
    }

    void removeCell(int a, int b){
        int bigsize = size*size;
        int num=gen[a][b]-1;
        bunchTips = new ArrayList<>();
        bunchTips.add(a*bigsize*bigsize + b*bigsize + num);
        gen[a][b]=0;

        for (int i=0; i<bigsize; i++){
            if (i==num)
                continue;
            weight[a*bigsize*bigsize + b*bigsize + i]--;
            if (weight[a*bigsize*bigsize + b*bigsize + i]==0){
                bunchTips.add(a*bigsize*bigsize + b*bigsize + i);
            }
        }
        for (int i=0; i<bigsize; i++){
            if (i==a)
                continue;
            weight[i*bigsize*bigsize + b*bigsize + num]--;
            if (weight[i*bigsize*bigsize + b*bigsize + num]==0){
                bunchTips.add(i*bigsize*bigsize + b*bigsize + num);
            }
        }

        for (int i=0; i<bigsize; i++){
            if (i==b)
                continue;
            weight[a*bigsize*bigsize + i*bigsize + num]--;
            if (i!=b && weight[a*bigsize*bigsize + i*bigsize + num]==0){
                bunchTips.add(a*bigsize*bigsize + i*bigsize + num);
            }


        }

        int aa=a/size, bb=b/size;
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                if (i+aa*size==a && j+bb*size==b)
                    continue;
                weight[(aa*size+i)*bigsize*bigsize + (bb*size+j)*bigsize + num]--;
                if (weight[(aa*size+i)*bigsize*bigsize + (bb*size+j)*bigsize + num]==0){
                    bunchTips.add((aa*size+i)*bigsize*bigsize + (bb*size+j)*bigsize + num);
                }

            }
        }
        weight[a*bigsize*bigsize + b*bigsize + num]=0;
    }


    boolean tryThis(int free){
        ArrayList<BitSet> preCliks = (ArrayList<BitSet>)currentCliks.clone();
        ArrayList<Integer> preCurrent = (ArrayList<Integer>)currentTips.clone();
        ArrayList<Integer> newTips = (ArrayList<Integer>)bunchTips.clone();
        for (int i=0; i<newTips.size(); i++){
            int tip = newTips.get(i);
            int a = tip/81;
            int b = tip/9%9;
            int n = tip%9;
            BitSet tipBit = new BitSet(729);
            for (int j=0; j<preCurrent.size(); j++){
                int aTip = preCurrent.get(j);
                int aa = aTip/81;
                int bb = aTip/9%9;
                int nn = aTip%9;
                if (!(aa==a && bb==b || ((aa==a || bb==b || (aa/size==a/size && bb/size==b/size)) && nn==n)))
                    tipBit.set(aTip, true);
            }
            //BitSet theOne = preCliks.get(0);
            //System.out.println("prenender " + tipBit + " got " + theOne + " need " + free);
            ArrayList<BitSet> newCliks = new ArrayList<>();
            for (int j=0; j<preCliks.size(); j++){
                BitSet curSet = preCliks.get(j);
                BitSet set = (BitSet)curSet.clone();
                set.and(tipBit);
                if (set.equals(curSet)){
                    curSet.set(tip, true);
                    preCliks.set(j, curSet);
                    continue;
                }
                boolean space=true;

                if (newCliks.size()>0)
                    for (int y=0; y<newCliks.size(); y++){
                        BitSet preSet = newCliks.get(y);
                        BitSet set1 = (BitSet)set.clone();
                        set1.and(preSet);
                        if (set1.equals(preSet))
                        {
                            space=false;
                            newCliks.set(y, set);
                        }
                        if (set1.equals(set))
                        {
                            space=false;
                        }
                    }

                set.set(tip, true);
                if (space){
                    newCliks.add(set);
                }
            }


            for (int j=0; j<newCliks.size(); j++){
                BitSet preSet = newCliks.get(j);
                boolean nonPart = true;
                for (int z=0; z < preCliks.size(); z++){
                    BitSet old = preCliks.get(z);
                    BitSet try1 = (BitSet)old.clone();
                    try1.and(preSet);
                    if (try1.equals(preSet)){
                        nonPart = false;
                        break;
                    }
                }
                if (nonPart){
                    preCliks.add(preSet);
                }
            }

            newCliks.clear();
            preCurrent.add(tip);
        }

        int count = 0;
        for (int j=0; j<preCliks.size(); j++){
            BitSet preSet = preCliks.get(j);
            if (preSet.cardinality()==free){
                count++;
            }
        }
        if (count!=1)
            return false;
        else{
            currentCliks = (ArrayList<BitSet>)preCliks.clone();
            currentTips = (ArrayList<Integer>)preCurrent.clone();
            return true;
        }
    }



    public int getVariants(int[][] g){
        int zeroes=0;
        int [][] h= new int[size*size][size*size];
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                if (g[i][j]==0)
                    zeroes++;
                else
                    h[i][j]=g[i][j];
            }
        }
        if (zeroes==0){
            return 1;
        }
        int count=0, a=-1, b=-1;
        List<Integer> minCell=new ArrayList<Integer>();
        for (int i=0; i<9; i++)
            minCell.add(i);
        List<Integer> Cell;
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                if (h[i][j]==0){
                    Cell=getCell(h, i ,j);
                    if (Cell.size()<minCell.size() && Cell.size()>0 || minCell.size()==0)
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

            h[a][b]=minCell.get(i);
            count+=getVariants(h);
            if (count>1)
                return count;
        }
        return count;
    }


    int brohnKerbosh(int free, ArrayList<Integer> compsub, ArrayList<Integer> candidates, ArrayList<Integer> not){
        int vars = 0;
        if (free == compsub.size())
            return 1;
        for (int i = 0; i < candidates.size(); i++){
            int cand = candidates.get(i);
            ArrayList<Integer> newCandidates = new ArrayList<>();
            compsub.add(cand);
            int a = cand/81;
            int b = cand/9%9;
            int n = cand%9;
            for (int j = i + 1; j < candidates.size(); j++){
                int newcand = candidates.get(j);
                int aa = newcand/81;
                int bb = newcand/9%9;
                int nn = newcand%9;
                if (!(aa==a && bb==b || ((aa==a || bb==b || (aa/3==a/3 && bb/3==b/3)) && nn==n)))
                    newCandidates.add(newcand);
                if (vars > 1)
                    return vars;
            }
            vars += brohnKerbosh(free, compsub, newCandidates, not);
            not.add(cand);
            compsub.remove(compsub.size() - 1);
        }
        return vars;
    }




    public List<Integer> getCell(int[][] g, int a, int b){
        int[] mVars={1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i=0; i<9; i++){
            if (i==b)
                continue;
            if (g[a][i]==0)
                continue;
            mVars[g[a][i]-1]=0;
        }
        for (int i=0; i<9; i++){
            if (i==a)
                continue;
            if (g[i][b]==0)
                continue;
            mVars[g[i][b]-1]=0;
        }
        int thisI=a/3, thisJ=b/3;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){

                if (a==i+thisI*3 && b==j+thisJ*3)
                    continue;
                if (g[i+thisI*3][j+thisJ*3]==0)
                    continue;
                mVars[g[i+thisI*3][j+thisJ*3]-1]=0;
            }
        }
        List<Integer> lis=new ArrayList<>();
        for (int i=0; i<9; i++)
            if (mVars[i]!=0)
                lis.add(mVars[i]);
        return lis;
    }

    public ArrayList<Integer> getCandidates(){
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int tip = 0; tip < 728; tip++){
            boolean good = true;
            int a1 = tip / 81;
            int b1 = tip / 9 % 9;
            int n1 = tip % 9;
            for (int z = 0; z < 9; z++){
                if (gen[z][b1]!=0 && gen[z][b1] == n1+1){
                    good = false;
                    break;
                }
            }
            for (int z = 0; z < 9; z++){
                if (gen[a1][z]!=0 && gen[a1][z] == n1+1){
                    good = false;
                    break;
                }
            }
            for (int z = 0; z < 9; z++){
                int a2 = (a1 / 3) + z / 3;
                int b2 = (b1 / 3) + z % 3;
                if (gen[a2][b2]!=0 && gen[a2][b2] == n1+1){
                    good = false;
                    break;
                }
            }
            if (good)
                candidates.add(tip);
        }
        return candidates;
    }

    public int[] getCommonCell(int[][] g, int a, int b){
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



    public void practiceGen(){
        long timeSimple = 0, timeMethod = 0, timeBrohn = 0;
        currentTips = new ArrayList<>();
        int[] bans = new int[81];
        correct=transp(gen);
        currentCliks = new ArrayList<>();
        int free=0;
        int a, b;
        BitSet bits = new BitSet(729);
        a = 0;
        b = 0;
        int tip = (a)*size*size*size*size + (b)*size*size + gen[a][b]-1;
        removeCell(a, b);
        bits.set(tip, true);
        currentTips.add(tip);
        free++;
        currentCliks.add(bits);
        int banned=0;
        while (banned+free<81){
            a=(int) (Math.random()*size*size);
            b=(int) (Math.random()*size*size);
            if (gen[a][b]>0 && bans[a*9+b]==0){
                free++;
                int num = gen[a][b];
                long start = System.currentTimeMillis();
                removeCell(a, b);
                timeMethod += System.currentTimeMillis() - start;
                start = System.currentTimeMillis();
                int count = getVariants(gen);
                timeSimple += System.currentTimeMillis() - start;
                start = System.currentTimeMillis();
                boolean one = tryThis(free);
                timeMethod += System.currentTimeMillis() - start;
                start = System.currentTimeMillis();
                ArrayList<Integer> not = new ArrayList<>();
                ArrayList<Integer> compsub = new ArrayList<>();
                ArrayList<Integer> candidates = getCandidates();
                int brohn = brohnKerbosh(free, compsub, candidates, not);
                timeBrohn += System.currentTimeMillis() - start;
                System.out.println(free);
                System.out.println(" method " + timeMethod + " simple " + timeSimple + " brohn " + timeBrohn);
                if (count>1){
                    banned++;
                    bans[a*9+b]=1;
                    free--;
                    setCell(a, b, num);
                }
            }
        }
        cur=transp(gen);
        lastCorrect=transp(gen);
    }


    public void makeField(int level){
        currentTips = new ArrayList<>();
        int[] bans = new int[81];
        correct = transp(gen);
        currentCliks = new ArrayList<>();
        int free = 0, banned = 0;
        int a, b;
        BitSet bits = new BitSet(729);
        for (int i=0; i < 9; i++){
            int h = i/3;
            int w = i%3;
            a=((int) (Math.random()*3)) + h*3;
            b=((int) (Math.random()*3)) + w*3;
            int tip = (a)*size*size*size*size + (b)*size*size + gen[a][b]-1;
            removeCell(a, b);
            bits.set(tip, true);
            currentTips.add(tip);
            free++;
        }
        currentCliks.add(bits);
        int clear = ((int) (Math.random()*3)) + level*3;
        //System.out.println(clear);
        while (clear > 0){
            a=(int) (Math.random()*9);
            b=(int) (Math.random()*9);
            int tip = (a)*size*size*size*size + (b)*size*size + gen[a][b]-1;
            if (gen[a][b]!=0 && bans[a*9+b]==0){
                int num = gen[a][b];
                removeCell(a, b);
                free++;
                clear--;
                int vars = getVariants(gen);
                System.out.println(vars);
                boolean one = tryThis(free);
                if (!one){
                    setCell(a, b, num);
                    free--;
                    clear++;
                    bans[a*9+b] = 1;
                }
            }
        }
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                if (free >= 25 + level * 5)
                    break;
                if (gen[i][j]!=0 && getCell(gen, i, j).size()==1)
                {
                    gen[i][j]=0;
                    free++;
                }
            }
            if (free >= 25 + level * 5)
                break;
        }
        cur=transp(gen);
        lastCorrect=transp(gen);

    }



    public int[][] transp(int[][] h){
        int a = size*size;
        int[][] l=new int[a][a];
        for (int i=0; i<a; i++){
            for (int j=0; j<a; j++)
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
        correct = swapTable(correct, size*size);
        gen = swapTable(gen, size*size);
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
        correct = swapTable(correct, size*size);
        gen = swapTable(gen, size*size);
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
        correct = swapTable(correct, size*size);
        gen = swapTable(gen, size*size);
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
        correct = swapTable(correct, size*size);
        gen = swapTable(gen, size*size);
        lastCorrect=transp(gen);
        cur=transp(gen);
    }

}