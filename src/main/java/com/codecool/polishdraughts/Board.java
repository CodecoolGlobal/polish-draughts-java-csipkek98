package com.codecool.polishdraughts;

import java.util.Arrays;

public class Board {
    private int boardSize;
    private Pawn[][] fields;

    public Board(int boardSize) {
        this.boardSize = boardSize;
        fields = new Pawn[boardSize][boardSize];
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (row % 2 != col % 2) {
                    fields[row][col] = new Pawn(2, row, col);
                }
            }
        }
        for (int row = boardSize - 4; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (row % 2 != col % 2) {
                    fields[row][col] = new Pawn(1, row, col);
                }
            }
        }
    }

    public Pawn[][] getFields() {
        return fields;
    }

    public void printBoard(Pawn[][] table) {
        String header = " ";
        for (int i = 1; i <=fields.length ; i++) {
            if(i<10){
                header = header.concat("  "+i);
            }else{
                header = header.concat(" "+i);
            }


        }
        System.out.println(header);

        char rowIndex = 'A';
        for (Pawn[] row : table) {
            System.out.print(rowIndex);
            rowIndex++;
            for (Pawn pawn : row){
                System.out.print("  ");
                if(pawn == null){
                    System.out.print(".");
                }
                else {
                    if (pawn.getColor() == 1){
                        System.out.print("⛂");
                    }else{
                        System.out.print("⛀");
                    }

                }
            }
            System.out.print("\n");
        }
    }
}
