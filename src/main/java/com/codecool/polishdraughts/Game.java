package com.codecool.polishdraughts;

import java.util.Arrays;
import java.util.Scanner;

public class Game {
    private Board board;
    private int boardSize;

    private final Scanner playerInput = new Scanner(System.in);

    public static void main(String[] args) {
        int round = 1;
        Game game = new Game();
        game.start();
        while(!game.checkForWinner()){
            int playerNumber = round % 2 == 1 ? 1 : 2;
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
            System.out.println("Wrong input, please insert a number between 10 and 20!");
            boardSize = playerInput.nextInt();
        }
        this.boardSize = boardSize;
        board = new Board(boardSize);
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
        TryToMakeMove(selectedPawnPosition);

    }

    private boolean checkForWinner(){
        return false;
        // need check later, now endless
    }

    private void TryToMakeMove(int[] pawnPosition){
        System.out.println("Where do you want to move this piece?");
        String input = playerInput.nextLine();
        while(!checkNewPosition(input, pawnPosition)){
            System.out.println("Invalid position, choose another!");
            input = playerInput.nextLine();
        }
        Pawn pawn = board.getFields()[pawnPosition[0]][pawnPosition[1]];
        int[] movePosition = convertInputToCoordinate(input);
        Coordinates coordinates = new Coordinates(movePosition[0],movePosition[1]);
        board.movePawn(pawn, coordinates);
    }

    private boolean checkSelectInput(int player, String input){
        if (checkMoveInput(input)) return false;
        int[] Coord = convertInputToCoordinate(input);
        if(checkIfPlayerPawn(player, Coord)){
            return true;
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

    private boolean checkNewPosition(String playerInput, int[] pawnPosition){
        if (checkMoveInput(playerInput)) return false;
        Pawn pawn = board.getFields()[pawnPosition[0]][pawnPosition[1]];
        int[] movePosition = convertInputToCoordinate(playerInput);
        return board.checkMove(pawn, movePosition[0], movePosition[1]);
    }


    private boolean checkIfPlayerPawn(int player, int[] playerCoord){
        try {
            board.getFields()[playerCoord[0]][playerCoord[1]].getColor();
        }catch (NullPointerException e){
            return false;
        }
        if(board.getFields()[playerCoord[0]][playerCoord[1]].getColor() == player){
            return true;
        }
        return false;
    }

    private int[] convertInputToCoordinate(String playerInput){
        int letter = Character.toLowerCase(playerInput.charAt(0))-'a';
        int number = Integer.parseInt(playerInput.substring(1))-1;
        return new int[]{letter, number};
    }
}
