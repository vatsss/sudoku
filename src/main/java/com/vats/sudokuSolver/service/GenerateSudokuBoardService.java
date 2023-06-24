package com.vats.sudokuSolver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GenerateSudokuBoardService {
    @Autowired
    SolveSudokuBoardService solveSudokuBoardService;
    public ArrayList<ArrayList<String>> getSudokuBoard(){
        int N = 9;
        int K = 20;
        SudokuService sudoku = new SudokuService(N,K);
        int[][] mat = sudoku.fillValues();
        return solveSudokuBoardService.formatSudokuBoard(mat);
    }
}
