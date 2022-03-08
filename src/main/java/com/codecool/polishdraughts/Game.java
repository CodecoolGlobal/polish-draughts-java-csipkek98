package com.codecool.polishdraughts;

import java.util.Arrays;
import java.util.Scanner;

public class Game {
    Pawn[][] Board;

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

    public Pawn[][] getBoard() {
        return Board;
    }

    public void setBoard(Pawn[][] board) {
        Board = board;
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
        Board board = new Board(boardSize);
        setBoard(board.getFields());
        board.printBoard();
    }

    private void playRound(int player){
        System.out.printf("Player %s's round!\n",player);
        System.out.println("Please select a pawn:");
        String input = playerInput.nextLine();
        while(!checkSelectInput(input)){
            System.out.println("Wrong input, please select one from your pawns!");
            input = playerInput.nextLine();
        }
        int[] selectedPawnPosition = convertInputToCoordinate(input);
        System.out.println(Arrays.toString(selectedPawnPosition));

    }

    private boolean checkForWinner(){
        return false;
        // need check later, now endless
    }

    private boolean checkSelectInput(String playerInput){
        char letter = Character.toLowerCase(playerInput.charAt(0));
        int number;
        try {
            number = Integer.parseInt(playerInput.substring(1));
        }catch (NumberFormatException e){
            return false;
        }
        if(Character.isDigit(letter) || (letter-'a')>Board.length){
            return false;
        }
        if(0>number-1 || number-1>Board.length){
            return false;
        }
        return true;
        // NEED MOVE VALIDATION HERE
//        return !checkIfAvailable(letter - 'a', number - 1);
    }

    private int[] convertInputToCoordinate(String playerInput){
        int letter = Character.toLowerCase(playerInput.charAt(0))-'a';
        int number = Integer.parseInt(playerInput.substring(1))-1;
        return new int[]{letter, number};
    }
}
