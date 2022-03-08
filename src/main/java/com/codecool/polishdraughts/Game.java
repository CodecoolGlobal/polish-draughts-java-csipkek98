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
        board.printBoard(Board);
    }

    private void playRound(int player){
        System.out.printf("Player %s's round!\n",player);
        System.out.println("Please select a pawn:");
        String input = playerInput.nextLine();
        while(!checkSelectInput(player, input)){
            input = playerInput.nextLine();
        }
        int[] selectedPawnPosition = convertInputToCoordinate(input);
        Coordinates coordinates = new Coordinates(selectedPawnPosition[0],selectedPawnPosition[1]);

    }

    private boolean checkForWinner(){
        return false;
        // need check later, now endless
    }

    private boolean checkSelectInput(int player, String playerInput){
        char letter = Character.toLowerCase(playerInput.charAt(0));
        int number;
        try {
            number = Integer.parseInt(playerInput.substring(1));
        }catch (NumberFormatException e){
            System.out.println("Need to end the input with a number!\n");
            return false;
        }
        if(Character.isDigit(letter) || (letter)>Board.length){
            System.out.println("Need to start with a letter or out of bounds!\n");
            return false;
        }
        if(0>number || number>Board.length){
            System.out.println("Out of bounds!\n");
            return false;
        }
        int[] Coord = convertInputToCoordinate(playerInput);
        if(checkIfPlayerPawn(player, Coord)){
            return true;
        }else{
            System.out.println("Please select a pawn from yours!");
            return false;
        }
    }
    private boolean checkIfPlayerPawn(int player, int[] playerCoord){
        try {
            Board[playerCoord[0]][playerCoord[1]].getColor();
        }catch (NullPointerException e){
            return false;
        }
        if(Board[playerCoord[0]][playerCoord[1]].getColor() == player){
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
