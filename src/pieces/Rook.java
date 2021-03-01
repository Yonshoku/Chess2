package pieces; 
 
import chessgui.Board;

public class Rook extends Piece{
    boolean hasMoved = false;

    public Rook(boolean isWhite) {
        super(isWhite, Type.ROOK, 5); 
    }

    @Override 
    public boolean isMoveCorrect(int fromX, int fromY, int toX, int toY) {
        // Check if piece tries to take same color piece
        if (doesCaptureOwnPiece(fromX, fromY, toX, toY)) 
            return false;

        // Same column 
        if (fromX == toX) {

            // Check if any pieces between
            int dir = fromY > toY ? -1 : 1;
            int fieldsBetween = Math.abs(fromY - toY) - 1;

            for (int i = 1; i <= fieldsBetween; i++) {
                if (position.getPiece(fromX, fromY + dir * i) != null)
                    return false;
            }

            hasMoved = true;
            return true;
        }

        // Same row
        if (fromY == toY) {

            int dir = fromX > toX ? -1 : 1;
            int fieldsBetween = Math.abs(fromX - toX) - 1;

            for (int i = 1; i <= fieldsBetween; i++) {
                if (position.getPiece(fromX + dir * i, fromY) != null)
                    return false;
            }

            hasMoved = true;
            return true;
        }

        // Neither same column nor same row
        return false;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

}
