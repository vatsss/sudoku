package com.vats.sudokuSolver.controller;

import com.vats.sudokuSolver.constants.ApiConstants;
import com.vats.sudokuSolver.service.GenerateSudokuBoardService;
import com.vats.sudokuSolver.service.SolveSudokuBoardService;
import com.vats.sudokuSolver.service.VerifySudokuSolutionService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class sudokuSolverController {
    @Autowired
    GenerateSudokuBoardService generateSudokuBoardService;
    @Autowired
    private VerifySudokuSolutionService verifySudokuSolution;
    @Autowired
    private SolveSudokuBoardService solveSudokuBoardService;

    enum Notifications{
        SAM_001 (ApiConstants.SAM_001,"sudoku board generated successfully."),
        SAM_002 (ApiConstants.SAM_002,"sudoku board solved successfully."),
        SAM_003 (ApiConstants.SAM_003,"Congrats! Your solution is correct! :)"),
        SAM_004 (ApiConstants.SAM_004,"Oops! Your solution is incorrect :( ");

        private final String code;
        private final String message;
        Notifications(String code, String message){
            this.code = code;
            this.message = message;
        }
    }

    @RequestMapping(value="/getSudokuBoard")
    public JSONObject getSudokuBoard(){
        ArrayList<ArrayList<String>> sudokuBoard = generateSudokuBoardService.getSudokuBoard();
        JSONObject jsonMapper = new JSONObject();
        jsonMapper.put(ApiConstants.GENERATED_SUDOKU_BOARD,sudokuBoard);
        jsonMapper.put(ApiConstants.STATUS,ApiConstants.SUCCESS);
        jsonMapper.put(ApiConstants.CODE,Notifications.SAM_001.code);
        jsonMapper.put(ApiConstants.MESSAGE,Notifications.SAM_001.message);
        return jsonMapper;
    }

    @RequestMapping(method = RequestMethod.POST,value="/solveSudokuBoard")
    public JSONObject solveSudokuBoard(@RequestBody Map<String,Object> userInfo){
        JSONObject jsonMapper = new JSONObject();
        ArrayList<ArrayList<String>> currentSudokuBoard = (ArrayList<ArrayList<String>>)userInfo.get(ApiConstants.GENERATED_SUDOKU_BOARD);
        ArrayList<ArrayList<String>> solvedSudokuBoard = solveSudokuBoardService.solveSudokuBoard(currentSudokuBoard);
        jsonMapper.put(ApiConstants.SOLVED_SUDOKU_BOARD,solvedSudokuBoard);
        jsonMapper.put(ApiConstants.STATUS,ApiConstants.SUCCESS);
        jsonMapper.put(ApiConstants.CODE,Notifications.SAM_002.code);
        jsonMapper.put(ApiConstants.MESSAGE,Notifications.SAM_002.message);
        return jsonMapper;
    }

    @RequestMapping(method = RequestMethod.POST,value="/validateSudokuBoard")
    public JSONObject validateSudokuBoard(@RequestBody Map<String,Object> userInfo){
        JSONObject jsonMapper = new JSONObject();
        ArrayList<ArrayList<String>> solvedSudokuBoard = (ArrayList<ArrayList<String>>)userInfo.get(ApiConstants.SOLVED_SUDOKU_BOARD);
        boolean boardHasCorrectSolution = verifySudokuSolution.validateSudokuSolution(solvedSudokuBoard);
        jsonMapper.put(ApiConstants.SOLVED_SUDOKU_BOARD,String.valueOf(boardHasCorrectSolution));
        jsonMapper.put(ApiConstants.STATUS,ApiConstants.SUCCESS);
        jsonMapper.put(ApiConstants.CODE,Notifications.SAM_003.code);
        jsonMapper.put(ApiConstants.MESSAGE,Notifications.SAM_003.message);
        if(!boardHasCorrectSolution){
            jsonMapper.put(ApiConstants.STATUS,ApiConstants.FAIL);
            jsonMapper.put(ApiConstants.CODE,Notifications.SAM_004.code);
            jsonMapper.put(ApiConstants.MESSAGE,Notifications.SAM_004.message);
        }
        return jsonMapper;
    }
}
