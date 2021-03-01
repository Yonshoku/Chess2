package pieces; 
 
import chessgui.Board;

public class King extends Piece{
    private boolean hasMoved = false;

    public King(boolean isWhite) {
        super(isWhite, Type.KING, 0); 
    }

    @Override 
    public boolean isMoveCorrect(int fromX, int fromY, int toX, int toY) {
        return true;
    }

    public boolean hasMoved() {
        return hasMoved;
    }
}
