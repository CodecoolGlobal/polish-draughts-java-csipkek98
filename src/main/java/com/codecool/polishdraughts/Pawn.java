package com.codecool.polishdraughts;


public class Pawn {
    private Color color;
    private Coordinates position;
    boolean isCrowned = false;

    public Pawn(int player, int row, int col){
        color = new Color(player);
        position = new Coordinates(row, col);
    }

    public int getColor() {
        return color.getColor();
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

    public void getCrown(){
        this.isCrowned = true;
    }
}

