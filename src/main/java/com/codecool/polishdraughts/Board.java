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

    public void printBoard() {
        for (Pawn[] row : fields) {
            System.out.println(Arrays.toString(row));
        }
    }
}
