package pieces; 
 
import chessgui.Board;

public class Rook extends Piece{
    boolean hasMoved = false;

    public Rook(int x, int y, boolean isWhite, Board board) {
        super(x, y, isWhite, Type.ROOK, 5, board); 
    }

    @Override 
    public boolean isMoveCorrect(int destX, int destY) {
        // Check if piece tries to take same color piece
        if (isTakesSameColorPiece(destX, destY)) 
            return false;
        
        int from, to;

        // Same column 
        if (getX() == destX) {
            // Check if there are pieces between
            from = Math.min(getY(), destY);
            to = Math.max(getY(), destY);
            
            for (int i = from + 1; i < to; i++) {
                if (board.getPiece(getX(), i) != null)
                    return false;
            }

            hasMoved = true;
            return true;
        }

        // Same row
        if (getY() == destY) {
            // Check if there are pieces between 
            from = Math.min(getX(), destX);
            to = Math.max(getX(), destX);
            
            for (int i = from + 1; i < to; i++) {
                if (board.getPiece(i, getY()) != null)
                    return false;
            } 

            hasMoved = true;
            return true;
        }

        // Neither same column nor same row
        return false;
    }

}
