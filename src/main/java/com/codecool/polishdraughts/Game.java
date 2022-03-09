package com.codecool.polishdraughts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Game {
    private Board board;
    private int boardSize;

    private final Scanner playerInput = new Scanner(System.in);


    public static void main(String[] args) {
        int round = 1;
        int enemyPlayer = 2;
        Game game = new Game();
        game.start();
        while(game.checkForWinner(enemyPlayer)){
            int playerNumber = round % 2 == 1 ? 1 : 2;
            enemyPlayer = playerNumber == 1 ? 2 : 1;
            game.playRound(playerNumber);
            round++;
        }
    }


    private void start(){
        System.out.println("Welcome to Polish Draughts!");
        System.out.println("Please choose a board size (10-20)");
        int boardSize = playerInput.nextInt();
        playerInput.nextLine();
        while (boardSize<10 || boardSize>20){
            if(boardSize == 420 || boardSize == 69){
                break;
            }
            System.out.println("Wrong input, please insert a number between 10 and 20!");
            boardSize = playerInput.nextInt();
        }
        if(boardSize<=20){
            this.boardSize = boardSize;
            board = new Board(boardSize);
        }else{
            System.out.println("Welcome to the secret menu kid, you cracked the code!");
            System.out.println("Which demo field would you like to choose?");
            System.out.println("1. Simple jump over enemy\n2. Get the crown!");
            int menuChoice = playerInput.nextInt();
            playerInput.nextLine();
            demoFieldInit(menuChoice);
        }

    }


    private void playRound(int player){
        board.printBoard();
        System.out.printf("Player %s's round!\n",player);
        System.out.println("Please select a pawn:");
        String input = playerInput.nextLine();
        while(!checkSelectInput(player, input)){
            input = playerInput.nextLine();
        }
        int[] selectedPawnPosition = convertInputToCoordinate(input);
        Pawn pawn = board.getFields()[selectedPawnPosition[0]][selectedPawnPosition[1]];
        TryToMakeMove(pawn);
    }


    private boolean checkForWinner(int enemyPlayer){
        for (int row = 0; row < boardSize; row++) {
            for(int col = 0; col < boardSize; col++){
                if(board.getColorFromCoordinate(row, col) == enemyPlayer) {
                    return true;
                }
            }
        }
        return false;
        // need to check for King vs King and no more step scenario
    }


    private void TryToMakeMove(Pawn selectedPawn){
        System.out.println("Where do you want to move this piece?");
        String input = playerInput.nextLine();
        while(!checkNewPosition(input, selectedPawn, false)){
            System.out.println("Invalid position, choose another!");
            input = playerInput.nextLine();
        }
        int[] movePosition = convertInputToCoordinate(input);
        Coordinates coordinates = new Coordinates(movePosition[0],movePosition[1]);
        if (board.isMoveTake(selectedPawn, coordinates)) {
            board.movePawn(selectedPawn, coordinates);
            if(board.canTakeEnemy(board.getPawnByCoords(coordinates))){
                board.printBoard();
                TryToTakePiece(board.getPawnByCoords(coordinates));
            }
        }
        else {
            board.movePawn(selectedPawn, coordinates);
        }
    }

    private void TryToTakePiece(Pawn selectedPawn){
        System.out.println("You can still take pieces!");
        Coordinates coordinates;
        String input = playerInput.nextLine();
        while (!checkNewPosition(input, selectedPawn, true)) {
            System.out.println("Invalid position, choose another!");
            input = playerInput.nextLine();
        }
        int[] movePosition = convertInputToCoordinate(input);
        coordinates = new Coordinates(movePosition[0], movePosition[1]);
        board.movePawn(selectedPawn, coordinates);
        if(board.canTakeEnemy(board.getPawnByCoords(coordinates))){
            board.printBoard();
            TryToTakePiece(board.getPawnByCoords(coordinates));
        }
    }


    private boolean checkSelectInput(int player, String input){
        if (checkIfQuit(input)) System.exit(0);
        if (checkMoveInput(input)) return false;
        int[] Coord = convertInputToCoordinate(input);
        if(checkIfPlayerPawn(player, Coord)){
            Pawn pawn = board.getFields()[Coord[0]][Coord[1]];
            if(board.canMove(pawn)){
                return true;
            }else{
                System.out.println("You can't move with that pawn!");
                return false;
            }
        }else{
            System.out.println("Please select a pawn from yours!");
            return false;
        }
    }


    private boolean checkMoveInput(String playerInput) {
        char letter = Character.toLowerCase(playerInput.charAt(0));
        int number;
        try {
            number = Integer.parseInt(playerInput.substring(1));
        }catch (NumberFormatException e){
            System.out.println("Need to end the input with a number!\n");
            return true;
        }
        if(Character.isDigit(letter) || letter-'a'>boardSize){
            System.out.println("Need to start with a letter or out of bounds!\n");
            return true;
        }
        if(0>number || number>boardSize){
            System.out.println("Out of bounds!\n");
            return true;
        }
        return false;
    }


    private boolean checkNewPosition(String playerInput, Pawn pawn, boolean onlyTake){
        if (checkIfQuit(playerInput)) System.exit(0);
        if (checkMoveInput(playerInput)) return false;
        int[] movePosition = convertInputToCoordinate(playerInput);
        if(onlyTake){
            return board.checkMove(pawn, movePosition[0], movePosition[1]) && board.isMoveTake(pawn, new Coordinates(movePosition[0], movePosition[1]));
        }
        else return board.checkMove(pawn, movePosition[0], movePosition[1]);
    }


    private boolean checkIfPlayerPawn(int player, int[] playerCoord){
        return board.getColorFromCoordinate(playerCoord[0], playerCoord[1]) == player;
    }


    private int[] convertInputToCoordinate(String playerInput){
        int letter = Character.toLowerCase(playerInput.charAt(0))-'a';
        int number = Integer.parseInt(playerInput.substring(1))-1;
        return new int[]{letter, number};
    }

    private void demoFieldInit(int choice){
        int testBoardSize = 10;
        this.boardSize = testBoardSize;
        this.board = new Board(testBoardSize, choice);
    }

    private boolean checkIfQuit(String input){
        if(Objects.equals(input, "quit") || Objects.equals(input, "exit")){
            return true;
        }
        return false;
    }
}
