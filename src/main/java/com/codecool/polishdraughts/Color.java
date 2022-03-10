package com.codecool.polishdraughts;



public class Color {
    private final int playerColor;
    private boolean isCrowned;
    private String symbol;

    public enum Colours{
        green(TerminalColors.GREEN_BRIGHT),
        blue(TerminalColors.BLUE_BRIGHT),
        yellow(TerminalColors.YELLOW_BRIGHT);

        public final String label;

        Colours(String asciiColor) {
            this.label = asciiColor;
        }

        public static String getLabelByPlayer(int player){
            switch (player){
                case 1:
                    return green.label;
                case 2:
                    return blue.label;
                default:
                    return yellow.label;
            }
        }
    }

    public enum Symbols{
        pawn("⛂"),
        king("⛃");

        public final String label;

        Symbols(String label) {
            this.label = label;
        }

        public static String getLabelByCrown(boolean isCrowned){
            if(isCrowned){
                return king.label;
            }
            else return pawn.label;
        }
    }

    public Color(int color){
        this.playerColor = color;
        symbol = Colours.getLabelByPlayer(color) + Symbols.pawn.label + TerminalColors.RESET;
        isCrowned = false;
    }

    public Color(int color, boolean isCrowned){
        this.playerColor = color;
        this.isCrowned = isCrowned;
        symbol = Colours.getLabelByPlayer(color) + Symbols.getLabelByCrown(isCrowned) + TerminalColors.RESET;
    }

    public void changeColor(String newColor){
        symbol = Colours.valueOf(newColor).label + Symbols.getLabelByCrown(isCrowned) + TerminalColors.RESET;
    }

    public void resetColor(){
        symbol = Colours.getLabelByPlayer(playerColor) + Symbols.getLabelByCrown(isCrowned) + TerminalColors.RESET;
    }

    public int getPlayerColor() {
        return playerColor;
    }

    public static String getPawnSymbol(int player) {
        return Colours.getLabelByPlayer(player) + Symbols.pawn.label + TerminalColors.RESET;
    }

    public String getSymbol() {
        return symbol;
    }
}
