package pieces;

import java.awt.image.*;

public class Piece {
    protected Type type;
    protected boolean isWhite;
    protected int x, y, value;

    public Piece(int x, int y, boolean isWhite, Type type, int value) {
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;
        this.type = type;
        this.value = value;
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
