package com.codecool.polishdraughts;


public class Pawn {
    private Color color;
    private Coordinates position;
    private boolean isCrowned = false;

    public Pawn(int player, int row, int col){
        color = new Color(player);
        position = new Coordinates(row, col);
    }

    public Pawn(int player, int row, int col, boolean isCrowned){
        color = new Color(player);
        position = new Coordinates(row, col);
        this.isCrowned = isCrowned;
    }

    public Pawn(int player, Coordinates position){
        color = new Color(player);
        this.position = position;
    }

    public Pawn(Pawn pawn){
        color = pawn.color;
        position = pawn.position;
        isCrowned = pawn.isCrowned;
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

    public void setCrowned(boolean crowned) {
        isCrowned = crowned;
    }

    public boolean getCrown(){
        return isCrowned;
    }

    public boolean isCorrectDirection(Coordinates newPos){
        if(color.getColor() == 1 && newPos.isUp()){
            return true;
        }
        return color.getColor() == 2 && !newPos.isUp();
    }

    public boolean isEnemy(Pawn pawn){
        return pawn != null && getColor() != pawn.getColor();
    }
}

