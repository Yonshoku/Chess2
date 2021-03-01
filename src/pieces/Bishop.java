package pieces; 
 
import chessgui.Board;

public class Bishop extends Piece{

    public Bishop(boolean isWhite) {
        super(isWhite, Type.BISHOP, 3);    
    }

    @Override
    public boolean isMoveCorrect(int fromX, int fromY, int toX, int toY) {

        // Check if it takes same color piece
        if (doesCaptureOwnPiece(fromX, fromY, toX, toY))
            return false;

        int fieldsBetween = Math.abs(fromX - toX) - 1;

        // Moves diagonally
        if (Math.abs(fromX - toX) == Math.abs(fromY - toY)) {

            // Check if any pieces between
            int dirX = fromX > toX ? -1 : 1;
            int dirY = fromY > toY ? -1 : 1;

            for (int i = 1; i <= fieldsBetween; i++) {
                if (position.getPiece(fromX + i * dirX, fromY + i * dirY) != null)
                    return false;
            }

            return true;
        }

        // Move isn't diagonal
        return false;
    }

}
