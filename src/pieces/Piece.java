package pieces; 

import chessgui.*;

import java.awt.image.*;

public class Piece {
    protected Type type;
    protected boolean isWhite;
    protected int value;
    protected Position position = Position.getPosition(); 

    public Piece(boolean isWhite, Type type, int value) {
        this.isWhite = isWhite;
        this.type = type;
        this.value = value;
    }

    public boolean doesCaptureOwnPiece(int fromX, int fromY, int toX, int toY) {
        if (position.getPiece(toX, toY) == null) 
            return false;

        return position.getPiece(fromX, fromY).isWhite() == position.getPiece(toX, toY).isWhite();
    }
    
    public boolean isMoveCorrect(int fromX, int fromY, int toX, int toY) {
        return true; 
    }

    public Type getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
    
    public boolean isWhite() {
        return isWhite;
    }
}
