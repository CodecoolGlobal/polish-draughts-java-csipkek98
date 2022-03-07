package com.codecool.polishdraughts;

public class Board {
    int boardSize;
    Pawn[][] fields;

    public Board (int boardSize){
        this.boardSize = boardSize;
        fields = new Pawn[boardSize][boardSize];
    }
}
