package com.vats.sudokuSolver.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SolveSudokuBoardService {
    public ArrayList<ArrayList<String>> solveSudokuBoard(ArrayList<ArrayList<String>> sudokuBoard){
        int n = sudokuBoard.size();

        int[][] grid = new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(sudokuBoard.get(i).get(j).equals(".")){
                    grid[i][j] = 0;
                }else{
                    grid[i][j] = Integer.parseInt(sudokuBoard.get(i).get(j));
                }
            }
        }
        if(solveSudoku(grid,0,0)){
           return formatSudokuBoard(grid);
        }else{
            throw new RuntimeException();
        }
    }
    private boolean solveSudoku(int[][] sudoku,int row,int col){
        int n = sudoku.length;
        if(row == n-1 && col == n) return true;
        if(col == n){
            row++;
            col = 0;
        }
        if(sudoku[row][col] != 0)
            return solveSudoku(sudoku,row,col+1);
        for(int i=1;i<=9;i++){
            if(isValid(sudoku,row,col,i)){
                sudoku[row][col] = i;
                if(solveSudoku(sudoku,row,col+1))
                    return true;
            }
            sudoku[row][col] = 0;
        }
        return false;
    }
    private boolean isValid(int[][] grid,int row,int col,int num){
        for (int x = 0; x <= 8; x++)
            if (grid[row][x] == num)
                return false;

        for (int x = 0; x <= 8; x++)
            if (grid[x][col] == num)
                return false;


        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (grid[i + startRow][j + startCol] == num)
                    return false;
        return true;
    }
    public ArrayList<ArrayList<String>> formatSudokuBoard(int[][] grid){
        ArrayList<ArrayList<String>> sudokuBoard = new ArrayList<>();
        for (int[] ints : grid) {
            ArrayList<String> rows = new ArrayList<>();
            for (int j = 0; j < ints.length; j++) {
                if (ints[j] == 0) {
                    rows.add(".");
                } else {
                    rows.add(String.valueOf(ints[j]));
                }
            }
            sudokuBoard.add(rows);
        }
        return sudokuBoard;
    }
}
