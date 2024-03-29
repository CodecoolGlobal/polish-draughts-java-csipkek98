package com.codecool.polishdraughts;

import java.util.ArrayList;

public class Board {
    private final int boardSize;
    private final Pawn[][] fields;
    private final String bgColor = TerminalColors.WHITE_BACKGROUND_BRIGHT+"   "+TerminalColors.RESET;

    public Board(int boardSize) {
        this.boardSize = boardSize;
        fields = new Pawn[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (row % 2 != col % 2 && row < 4) {
                    fields[row][col] = new Pawn(2, row, col);
                }else if (row % 2 != col % 2 && row > boardSize-5) {
                    fields[row][col] = new Pawn(1, row, col);
                }
            }
        }
    }

    public Board(int boardSize, int testNumber){
        this.boardSize = boardSize;
        fields = new Pawn[boardSize][boardSize];
        switch (testNumber){
            case 1:
                fields[7][6] = new Pawn(1,7,6);
                fields[6][5] = new Pawn(2,6,5);
                break;
            case 2:
                fields[1][6] = new Pawn(1,1,6);
                fields[2][7] = new Pawn(2,2,7);
                fields[1][8] = new Pawn(2,1,8);
                break;
            case 3:
                fields[1][8] = new Pawn(1,1,8);
                fields[3][6] = new Pawn(1,3,6);
                fields[0][9] = new Pawn(2,0,9);
                break;
            case 4:
                fields[2][1] = new Pawn(1,2,1, true);
                fields[1][2] = new Pawn(2,1,2);
                fields[1][4] = new Pawn(2,1,4);
                fields[3][4] = new Pawn(2,3,4);
                fields[7][8] = new Pawn(2,7,8);
                break;
            case 5:
                fields[1][0] = new Pawn(1,1,0);
                fields[8][1] = new Pawn(2,8,1);
                break;
        }
    }

    public Board(Board board){
        boardSize = board.boardSize;
        fields = new Pawn[boardSize][boardSize];
        for (int i = 0; i < board.boardSize; i++) {
            for (int j = 0; j < board.boardSize; j++) {
                if(board.fields[i][j] != null) {
                    fields[i][j] = new Pawn(board.fields[i][j]);
                }
            }
        }
    }

    public Pawn[][] getFields() {
        return fields;
    }

    public Pawn getPawnByCoords(Coordinates field){
        return fields[field.getX()][field.getY()];
    }

    public void printBoard() {
        String header = " ";
        for (int i = 1; i <=fields.length ; i++) {
            if(i<11){
                header = header.concat("  "+i);
            }else{
                header = header.concat(" "+i);
            }
        }
        System.out.println(header);

        int rowCounter = 0;
        int colCounter = 0;
        for (Pawn[] row : fields) {
            System.out.print((char)('A'+rowCounter));
            if(rowCounter%2 == 1){
                System.out.print(" ");
            }
            rowCounter++;
            for (Pawn pawn : row){
                System.out.print(" ");
                if(pawn == null && (colCounter%2 != rowCounter%2)){
                    System.out.print(bgColor);
                }else if(pawn == null && (colCounter%2 == rowCounter%2)){
                    System.out.print(" ");
                }
                else {
                    System.out.print(pawn.getSymbol());
                }
                colCounter++;
            }
            System.out.print("\n");
        }
    }

    public boolean checkMove(Pawn pawn, int row, int col){
        Coordinates newField = new Coordinates(row, col);
        Coordinates distance = pawn.getPosition().getDifference(newField);

        if(!isInBoard(newField)){
            return false;
        }
        else if(!isFieldEmpty(newField)){
            return false;
        }
        else if(distance.isSymmetric()){
            if(pawn.getCrown()){
                return checkKingMove(pawn, newField);
            }
            else return checkPawnMove(pawn, newField, distance);
        }
        return false;
    }

    public boolean isFieldEmpty(Coordinates field){
        return fields[field.getX()][field.getY()] == null;
    }

    public void removePawn(Coordinates field){
        fields[field.getX()][field.getY()] = null;
    }

    public void movePawn(Pawn pawn, Coordinates field){
        Coordinates original = pawn.getPosition();
        fields[pawn.getPosition().getX()][pawn.getPosition().getY()] = null;

        if(pawn.getCrown()) {
            Coordinates[] betweenFields = pawn.getPosition().getInBetweens(field);
            for (Coordinates middleField : betweenFields) {
                if (middleField != null){
                    removePawn(middleField);
                }
            }
        }
        else {
            if (original.getDifference(field).howManyCell() == 2){
                removePawn(original.getMiddle(field));
            }
            crownPawn(pawn, field);
        }

        pawn.setPosition(field.getX(), field.getY());
        fields[field.getX()][field.getY()] = pawn;
    }

    public boolean isInBoard(Coordinates field){
        return field.getX() < boardSize && field.getY() < boardSize && 0 <= field.getX() && 0 <= field.getY();
    }

    public boolean canTakeEnemy(Pawn pawn){
        Coordinates[] neighbours = pawn.getPosition().getDiagNeighbours(2);
        for(Coordinates field: neighbours){
            if (isInBoard(field) && isFieldEmpty(field)) {
                Coordinates middle = pawn.getPosition().getMiddle(field);
                if (pawn.isEnemy(fields[middle.getX()][middle.getY()])) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canMove(Pawn pawn){
        Coordinates[] neighbours = pawn.getPosition().getDiagNeighbours(1);
        for(Coordinates field: neighbours){
            if (isInBoard(field) && isFieldEmpty(field)) {
                if (pawn.getCrown() || pawn.isCorrectDirection(pawn.getPosition().getDifference(field))) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getColorFromCoordinate(int x, int y){
        boolean isPawn = fields[x][y] != null;
        if(isPawn){
            return fields[x][y].getPlayerColor();
        }else{
            return 0;
        }
    }

    public void crownPawn(Pawn pawn, Coordinates field){
        if((field.getX() == 0 && pawn.getPlayerColor() == 1) || (field.getX() == boardSize-1 && pawn.getPlayerColor() == 2)){
            if(!pawn.getCrown()){
                pawn.setCrowned(true);
            }
        }
    }

    public boolean checkPawnMove(Pawn pawn, Coordinates newField, Coordinates distance) {
        if (pawn.isCorrectDirection(distance) || isMoveTake(pawn, newField)) {
            switch (distance.howManyCell()) {
                case 1:
                    return isFieldEmpty(newField);
                case 2:
                    Coordinates middleField = pawn.getPosition().getMiddle(newField);
                    if (fields[middleField.getX()][middleField.getY()] == null) {
                        return false;
                    } else {
                        int middleColor = fields[middleField.getX()][middleField.getY()].getPlayerColor();
                        return middleColor != pawn.getPlayerColor();
                    }
                default:
                    return false;
            }
        }
        return false;
    }

    public boolean checkKingMove(Pawn pawn, Coordinates newField){
        Coordinates[] betweenFields = pawn.getPosition().getInBetweens(newField);
        int pawns = 0;if (betweenFields != null) {
            for (Coordinates field : betweenFields) {
                if (field != null) {
                    if (!isFieldEmpty(field)) {
                        pawns++;
                    }
                }
            }
        }
        return pawns <= 1;
    }

    public int countKings(int player){
        ArrayList<Pawn> kings = selectPawns();
        kings.removeIf(pawn -> !pawn.getCrown() || pawn.getPlayerColor() != player);
        return kings.size();
    }

    public ArrayList<Pawn> selectPawns(){
        ArrayList<Pawn> pawns = new ArrayList<Pawn>();
        for (Pawn[] row: fields) {
            for (Pawn pawn: row) {
                if (pawn != null){
                    pawns.add(pawn);
                }
            }
        }
        return pawns;
    }

    public int countPawns(){
        return selectPawns().size();
    }

    public ArrayList<Pawn> selectEnemyPawns(int player){
        ArrayList<Pawn> pawns = selectPawns();
        pawns.removeIf(pawn -> pawn.getCrown() || pawn.getPlayerColor() != player);
        return pawns;
    }

    public boolean isMoveTake(Pawn pawn, Coordinates newField){
        Board tempBoard = new Board(this);
        int oldPawnNumber = tempBoard.countPawns();

        tempBoard.movePawn(tempBoard.getPawnByCoords(pawn.getPosition()), newField);
        int newPawnNumber = tempBoard.countPawns();
        return newPawnNumber < oldPawnNumber;
    }
}
