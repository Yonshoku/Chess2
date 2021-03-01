package pieces; 
 
import chessgui.*;

public class Knight extends Piece{
    public Knight(boolean isWhite) {
        super(isWhite, Type.KNIGHT, 3);  
    }
    
    @Override
    public boolean isMoveCorrect(int fromX, int fromY, int toX, int toY) {
        if (doesCaptureOwnPiece(fromX, fromY, toX, toY))
            return false;
        
        int x = Math.abs(fromX - toX);
        int y = Math.abs(fromY - toY);

        return x * y == 2;
    }
}
