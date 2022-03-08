package com.codecool.polishdraughts;

public class Coordinates {
    private int x;
    private int y;

    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinates getDifference(Coordinates coordinates){
        int x = this.x - coordinates.getX();
        int y = this.y - coordinates.getY();
        Coordinates diff = new Coordinates(x, y);
        return diff;
    }

    public boolean isSymmetric(){
        return x == y;
    }

    public int howManyCell(){
        return Math.max(x, y);
    }
}
