package com.vats.sudokuSolver.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class VerifySudokuSolutionService {
    public boolean validateSudokuSolution(ArrayList<ArrayList<String>> sudokuBoard){
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

        return verifySudokuSolution(grid);
    }
    private boolean verifySudokuSolution(int[][] board){
        int N = board.length;
        if (!isInRange(board)) {
            return false;
        }
        boolean[] unique = new boolean[N + 1];
        for(int i = 0; i < N; i++) {
            Arrays.fill(unique, false);
            for(int j = 0; j < N; j++) {
                int Z = board[i][j];
                if (unique[Z]) {
                    return false;
                }
                unique[Z] = true;
            }
        }
        for(int i = 0; i < N; i++) {
            Arrays.fill(unique, false);
            for(int j = 0; j < N; j++) {
                int Z = board[j][i];
                if (unique[Z]) {
                    return false;
                }
                unique[Z] = true;
            }
        }
        for(int i = 0; i < N - 2; i += 3)
        {
            for(int j = 0; j < N - 2; j += 3)
            {
                Arrays.fill(unique, false);
                for(int k = 0; k < 3; k++)
                {
                    for(int l = 0; l < 3; l++)
                    {
                        int X = i + k;
                        int Y = j + l;
                        int Z = board[X][Y];
                        if (unique[Z]) {
                            return false;
                        }
                        unique[Z] = true;
                    }
                }
            }
        }
        return true;
    }
    private boolean isInRange(int[][] board) {
        int N = board.length;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if (board[i][j] <= 0 || board[i][j] > 9){
                    return false;
                }
            }
        }
        return true;
    }
}
