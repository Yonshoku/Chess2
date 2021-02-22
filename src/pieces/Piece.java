package pieces; 

import chessgui.Board;

import java.awt.image.*;

public class Piece {
    protected Type type;
    protected boolean isWhite;
    protected int x, y, value;
    protected Board board;

    public Piece(int x, int y, boolean isWhite, Type type, int value, Board board) {
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;
        this.type = type;
        this.value = value;
        this.board = board;
    }
    
    public boolean isMoveCorrect(int destX, int destY) {
        return true; 
    }

    public boolean isTakesSameColorPiece(int destX, int destY) {
        Piece toCapture = board.getPiece(destX, destY);

        if (board.getActivePiece() != null && toCapture != null) {
            return board.getActivePiece().isWhite() == toCapture.isWhite();
        }

        return false;
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

    public int getX() {
        return x;
    }
    
    public void setX(int x){
        this.x = x;
    }

    public int getY() {
        return y;
    } 
    
    public void setY(int y) {
        this.y = y;
    } 
}
