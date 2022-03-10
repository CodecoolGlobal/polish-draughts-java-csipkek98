package com.codecool.polishdraughts;



public class Color {
    private final String BgColor = TerminalColors.WHITE_BACKGROUND_BRIGHT+"   "+TerminalColors.RESET;
    private int color;
    private String symbol;

    public Color(int color){
        this.color = color;
    }

    public Color(int color, boolean crowned){
        this.color = color;
        if (color == 1 && !crowned){
            symbol = TerminalColors.GREEN_BRIGHT+"⛂"+TerminalColors.RESET;
        }else if(color == 2 && !crowned){
            symbol = TerminalColors.BLUE_BRIGHT+"⛂"+TerminalColors.RESET;
        }else if(color == 1 && crowned){
            symbol = TerminalColors.GREEN_BRIGHT+"⛃"+TerminalColors.RESET;
        }else if(color == 2 && crowned){
            symbol = TerminalColors.BLUE_BRIGHT+"⛃"+TerminalColors.RESET;
        }
    }

    public String getSymbol(){
        return symbol;
    }

    public int getColor() {
        return color;
    }
}
