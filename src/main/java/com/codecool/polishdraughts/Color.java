package com.codecool.polishdraughts;



public class Color {
    private final int playerColor;
    private String symbol;

    public enum Colours{
        green(TerminalColors.GREEN_BRIGHT),
        blue(TerminalColors.BLUE_BRIGHT),
        yellow(TerminalColors.YELLOW_BRIGHT);

        public final String label;

        Colours(String asciiColor) {
            this.label = asciiColor;
        }
    }

    public enum Symbols{
        pawn("⛂"),
        king("⛃");

        public final String label;

        Symbols(String label) {
            this.label = label;
        }
    }

    public Color(int color){
        this.playerColor = color;
        switch (color){
            case 1:
                symbol = Colours.green.label + Symbols.pawn.label;
                break;
            case 2:
                symbol = Colours.blue.label + Symbols.pawn.label;
                break;
        }
    }

    public int getPlayerColor() {
        return playerColor;
    }
}
