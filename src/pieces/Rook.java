package pieces; 
 
import chessgui.Board;

public class Rook extends Piece{

    public Rook(int x, int y, boolean isWhite, Board board) {
        super(x, y, isWhite, Type.ROOK, 5, board); 
    }

    @Override 
    public boolean isMoveCorrect(int destX, int destY) {
        int startX = board.getActivePieceX();
        int startY = board.getActivePieceY();

        // Check if piece tries to take same color piece
        if (isTakesSameColorPiece(destX, destY)) 
            return false;
        
        int from, to;

        // Same column 
        if (startX == destX) {
            // Check if there are pieces between
            from = Math.min(startY, destY);
            to = Math.max(startY, destY);
            
            for (int i = from + 1; i < to; i++) {
                if (board.getPiece(startX, i) != null)
                    return false;
            }

            return true;
        }

        // Same row
        if (startY == destY) {
            // Check if there are pieces between 
            from = Math.min(startX, destX);
            to = Math.max(startX, destX);
            
            for (int i = from + 1; i < to; i++) {
                if (board.getPiece(i, startY) != null)
                    return false;
            } 

            return true;
        }

        // Neither same column nor same row
        return false;
    }

}
