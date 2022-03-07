package com.codecool.polishdraughts;


public class Pawn {
    private Color color;
    private Coordinates position;

    public Pawn(int player, int row, int col){
        color = new Color(player);
        position = new Coordinates(row, col);
    }

    public Color getColor() {
        return color;
    }

    public Coordinates getPosition() {
        return position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }

    public void setPosition(int row, int col){
        this.position = new Coordinates(row, col);
    }

    public boolean checkMove(Pawn[][] fields, int row, int col){
        if(fields[row][col] == null){
            return true;
//            need some adjustments for game rule checks
        }else{
            return false;
        }
    }
}

